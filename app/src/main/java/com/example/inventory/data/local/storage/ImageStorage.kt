package com.example.inventory.data.local.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class ImageStorage @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    fun saveBitmapStorage(bitmap: Bitmap): String? {
        val fileName = "img_${System.currentTimeMillis()}.png"   // .png no .jpg
        val file = File(context.filesDir, fileName)

        try{
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            return file.absolutePath
        }catch (e: Exception){
            Log.e("Exception",e.toString())
            return null
        }

    }

    fun loadBitmapStorage(path: String): Bitmap? {
        return BitmapFactory.decodeFile(path)
    }

    fun deleteImageStorage(path: String) {
        File(path).delete()
    }
}