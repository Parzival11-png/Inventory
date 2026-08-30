package com.example.inventory.domain.model

import coil3.Bitmap

data class Item(
    val id : Int,
    val name : String,
    val bitmap: Bitmap,
    val description: String,
    val dateAdded : Long
)
