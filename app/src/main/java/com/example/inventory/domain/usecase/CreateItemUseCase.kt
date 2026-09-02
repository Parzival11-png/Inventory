package com.example.inventory.domain.usecase


import android.graphics.Bitmap
import android.util.Log
import com.example.inventory.domain.model.Item
import com.example.inventory.domain.model.ItemToSave
import com.example.inventory.domain.repository.ItemRepository
import javax.inject.Inject

class CreateItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(name: String, description: String , bitmapResult : Bitmap){
        val imgPath = repository.saveImageStorage(bitmapResult)
        imgPath?.let {
            Log.d("UseCase", "Saving Item")
            repository.insertItem(
                ItemToSave(
                    name = name,
                    imagePath = it,
                    description = description
                )
            )
        }

    }
}