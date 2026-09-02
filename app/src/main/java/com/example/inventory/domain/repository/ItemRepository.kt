package com.example.inventory.domain.repository

import android.graphics.Bitmap
import com.example.inventory.domain.model.Item
import com.example.inventory.domain.model.ItemToSave

interface ItemRepository {
    suspend fun getItems(): List<Item>
    suspend fun insertItem(item: ItemToSave)
    suspend fun deleteItem(item: Item)

    suspend fun saveImageStorage(bitmap: Bitmap) : String?
}