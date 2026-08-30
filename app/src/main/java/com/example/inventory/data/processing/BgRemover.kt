package com.example.inventory.data.processing

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.core.graphics.scale
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.FloatBuffer
import javax.inject.Inject
import androidx.core.graphics.createBitmap

class BgRemover @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var ortEnv: OrtEnvironment? = null
    private var ortSession: OrtSession? = null
    private val inputSize = 320

    init {
        setupOnnx()
    }

    private fun setupOnnx() {
        try {
            ortEnv = OrtEnvironment.getEnvironment()
            val modelBytes = context.assets.open("u2netp.onnx").readBytes()
            ortSession = ortEnv?.createSession(modelBytes)
            Log.d("Home", "U2Netp ONNX cargado exitosamente.")
        } catch (e: Exception) {
            Log.e("Home", "Error al inicializar U2Netp ONNX", e)
        }
    }

    fun removeBg(inputBitmap: Bitmap?): Bitmap? {
        if (inputBitmap == null) return null
        val env = ortEnv ?: return null
        val session = ortSession ?: return null

        return try {
            val safeBitmap = if (inputBitmap.config == Bitmap.Config.HARDWARE) {
                inputBitmap.copy(Bitmap.Config.ARGB_8888, false)
            } else {
                inputBitmap
            }

            // 1. Redimensionar a 320x320
            val resized = safeBitmap.scale(inputSize, inputSize)

            // 2. Preprocesar en formato NCHW [1, 3, 320, 320] con normalización ImageNet
            val floatBuffer = FloatBuffer.allocate(1 * 3 * inputSize * inputSize)
            val pixels = IntArray(inputSize * inputSize)
            resized.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

            val mean = floatArrayOf(0.485f, 0.456f, 0.406f)
            val std = floatArrayOf(0.229f, 0.224f, 0.225f)

            // Canal R
            for (pixel in pixels) {
                val r = Color.red(pixel) / 255.0f
                floatBuffer.put((r - mean[0]) / std[0])
            }
            // Canal G
            for (pixel in pixels) {
                val g = Color.green(pixel) / 255.0f
                floatBuffer.put((g - mean[1]) / std[1])
            }
            // Canal B
            for (pixel in pixels) {
                val b = Color.blue(pixel) / 255.0f
                floatBuffer.put((b - mean[2]) / std[2])
            }
            floatBuffer.rewind()

            // 3. Crear tensor de entrada
            val inputTensor = OnnxTensor.createTensor(
                env,
                floatBuffer,
                longArrayOf(1, 3, inputSize.toLong(), inputSize.toLong())
            )

            // 4. Inferencia
            val inputName = session.inputNames.iterator().next()
            val results = session.run(mapOf(inputName to inputTensor))

            // La salida principal combinada es d0 [1, 1, 320, 320]
            @Suppress("UNCHECKED_CAST")
            val rawOutput = (results[0].value as Array<Array<Array<FloatArray>>>)[0][0]

            // 5. Encontrar min y max para normalizar la máscara a [0.0, 1.0]
            var minVal = Float.MAX_VALUE
            var maxVal = Float.MIN_VALUE
            for (y in 0 until inputSize) {
                for (x in 0 until inputSize) {
                    val v = rawOutput[y][x]
                    if (v < minVal) minVal = v
                    if (v > maxVal) maxVal = v
                }
            }

            val range = if (maxVal - minVal == 0f) 1f else (maxVal - minVal)
            val maskBitmap = Bitmap.createBitmap(inputSize, inputSize, Bitmap.Config.ARGB_8888)
            val maskPixels = IntArray(inputSize * inputSize)

            for (y in 0 until inputSize) {
                for (x in 0 until inputSize) {
                    val normalized = (rawOutput[y][x] - minVal) / range
                    val alpha = (normalized * 255).toInt().coerceIn(0, 255)
                    maskPixels[y * inputSize + x] = Color.argb(alpha, 255, 255, 255)
                }
            }
            maskBitmap.setPixels(maskPixels, 0, inputSize, 0, 0, inputSize, inputSize)

            // 6. Escalar máscara al tamaño original y aplicar canal Alpha
            val scaledMask = maskBitmap.scale(safeBitmap.width, safeBitmap.height)
            val finalPixels = IntArray(safeBitmap.width * safeBitmap.height)
            val origPixels = IntArray(safeBitmap.width * safeBitmap.height)
            val maskFullPixels = IntArray(safeBitmap.width * safeBitmap.height)

            safeBitmap.getPixels(origPixels, 0, safeBitmap.width, 0, 0, safeBitmap.width, safeBitmap.height)
            scaledMask.getPixels(maskFullPixels, 0, safeBitmap.width, 0, 0, safeBitmap.width, safeBitmap.height)

            for (i in origPixels.indices) {
                val alpha = Color.alpha(maskFullPixels[i])
                if (alpha < 45) { // Filtrar fondo
                    finalPixels[i] = Color.TRANSPARENT
                } else {
                    val r = Color.red(origPixels[i])
                    val g = Color.green(origPixels[i])
                    val b = Color.blue(origPixels[i])
                    finalPixels[i] = Color.argb(alpha, r, g, b)
                }
            }

            val resultBitmap = createBitmap(safeBitmap.width, safeBitmap.height)
            resultBitmap.setPixels(finalPixels, 0, safeBitmap.width, 0, 0, safeBitmap.width, safeBitmap.height)
            resultBitmap

        } catch (e: Exception) {
            Log.e("BgRemover", "Excepción al procesar imagen con ONNX", e)
            null
        }
    }
    fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                decoder.isMutableRequired = true
                decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            }
        } catch (e: Exception) {
            Log.e("BgRemover", "Error al convertir Uri a Bitmap", e)
            null
        }
    }

    fun toPixelArt(input: Bitmap?, targetResolution: Int, colorLevels: Int): Bitmap? {
        if(input == null) return null
        // 1. Reducir a baja resolución usando Nearest Neighbor (sin filtro bilineal para no desenfocar)
        val smallBitmap = input.scale(targetResolution, targetResolution, false)

        val width = smallBitmap.width
        val height = smallBitmap.height
        val pixels = IntArray(width * height)
        smallBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        // 2. Posterización / Reducción de paleta de colores
        val step = 256 / colorLevels
        for (i in pixels.indices) {
            val color = pixels[i]
            val alpha = Color.alpha(color)

            if (alpha > 100) { // Conservar píxeles del objeto
                val r = (Color.red(color) / step) * step
                val g = (Color.green(color) / step) * step
                val b = (Color.blue(color) / step) * step
                pixels[i] = Color.argb(255, r, g, b)
            } else {
                pixels[i] = Color.TRANSPARENT
            }
        }

        val quantizedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //val quantizedBitmap = createBitmap(width, height)
        quantizedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        // 3. Re-escalar al tamaño deseado para visualización nítida
        return quantizedBitmap.scale(256, 256, false)
    }


}