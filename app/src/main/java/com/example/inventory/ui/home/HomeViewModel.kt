package com.example.inventory.ui.home

import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import com.example.inventory.data.processing.BgRemover
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val bgRemover: BgRemover,
) : ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: HomeAction){
        when(action){
            HomeAction.PickPhoto -> pickPhoto()
            HomeAction.ProcessImage -> processImage()
            HomeAction.AddPixels -> addPixels()
            HomeAction.SubtractPixels -> subtractPixels()
        }
    }



    // WOMps
    fun setCurrentPhoto(photo : Uri?){
        Log.d("Home", "Choosing Photo")
       photo?.let { nonNullPhoto ->
           Log.d("Home", "Photo selected : $nonNullPhoto")
           _uiState.update {
               it.copy(
                   selectedPhotoUri = nonNullPhoto
               )
           }
       }
    }

    private fun processImage() {
        Log.d("Home", "Trying to process image")
        val uriPhoto = uiState.value.selectedPhotoUri ?: return
        val pixels = _uiState.value.pixelRes
        viewModelScope.launch {
            _uiState.update { it.copy(processingImage = true ) }
            val resultImg = withContext(Dispatchers.Default){
                val bitmapPhoto = bgRemover.uriToBitmap(uriPhoto)
                val resultBitmap = bgRemover.removeBg(bitmapPhoto)
                val pixelBitmap = bgRemover.toPixelArt(resultBitmap, pixels,8)
                pixelBitmap
            }
            _uiState.update {
                it.copy(
                    resultImage = resultImg,
                    processingImage = false
                )
            }
        }
    }



    private fun getImageRes(uri: Uri){
        try {
            context.contentResolver.openInputStream(uri).use { inputStream ->
                // 2. Configurar las opciones para que SOLO lea los metadatos (tamaño)
                val opciones = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }

                // 3. Decodificar el stream (no cargará la imagen, solo llenará las opciones)
                BitmapFactory.decodeStream(inputStream, null, opciones)

                // 4. Retornar el IntSize con el ancho y alto obtenidos
                val result = IntSize(width = opciones.outWidth, height = opciones.outHeight)

                _uiState.update {
                    it.copy(
                        imageRes = result
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update {
                it.copy(
                    imageRes = IntSize.Zero
                )
            }
        }
    }
    private fun pickPhoto(){
        uiState.value.selectedPhotoUri?.let {
            getImageRes(it)
        }
    }
    private fun addPixels(){
        if (_uiState.value.pixelRes < 512) _uiState.update { it.copy(pixelRes = (it.pixelRes * 2)) }
    }
    private fun subtractPixels(){
        if (_uiState.value.pixelRes > 8) _uiState.update { it.copy(pixelRes = (it.pixelRes / 2)) }
    }

}