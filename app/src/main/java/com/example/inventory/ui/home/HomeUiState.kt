package com.example.inventory.ui.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.unit.IntSize
import com.example.inventory.domain.model.Item

data class HomeUiState(
    val processingImage : Boolean = false,

    val selectedPhotoUri : Uri? = null,
    val resultImage : Bitmap? = null,

    val imageRes : IntSize = IntSize.Zero,
    val pixelRes : Int = 8,


    val inventoryList : List<Item> = listOf()

)