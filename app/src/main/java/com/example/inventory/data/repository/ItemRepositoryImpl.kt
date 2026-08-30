package com.example.inventory.data.repository

import android.graphics.Bitmap
import com.example.inventory.data.local.ItemDao
import com.example.inventory.data.local.ItemEntity
import com.example.inventory.data.local.storage.ImageStorage
import com.example.inventory.domain.repository.ItemRepository
import com.example.inventory.domain.model.Item
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val dao: ItemDao,
    private val imageStorage: ImageStorage
) : ItemRepository {

    override suspend fun getItems(): List<Item> {
        return dao.getItems().map { entity ->
            Item(
                id = entity.id,
                name = entity.name,
                imagePath = entity.imagePath,
                description = entity.description,
                dateAdded = entity.dateAdded
            )

        }
    }

    override suspend fun insertItem(item: Item) {
        val entity = ItemEntity(
            id = item.id,
            name = item.name,
            imagePath = item.imagePath,
            description = item.description,
        )
        dao.insertItem(entity)
    }

    override suspend fun deleteItem(item: Item) {
        val entity = ItemEntity(
            id = item.id,
            name = item.name,
            imagePath = item.imagePath,
            description = item.description,
        )
        dao.deleteItem(entity)
    }

    override suspend fun saveImageStorage(bitmap: Bitmap): String? {
        return imageStorage.saveBitmapStorage(bitmap)
    }
}