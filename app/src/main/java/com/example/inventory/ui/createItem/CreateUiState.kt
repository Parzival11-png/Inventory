package com.example.inventory.ui.createItem

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.unit.IntSize

data class CreateUiState(
    val currentName : String = "",
    val currentDescription : String = "",

    val selectedPhotoUri : Uri? = null,
    val resultImage : Bitmap? = null,

    val processingImage : Boolean = false,
    val currentPixelRes : Int = 8,
)
