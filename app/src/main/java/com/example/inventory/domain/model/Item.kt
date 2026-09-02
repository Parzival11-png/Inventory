package com.example.inventory.domain.model

import coil3.Bitmap

data class Item(
    val id : Int,
    val name : String,
    val imagePath: String,
    val description: String,
    val dateAdded : Long
)
