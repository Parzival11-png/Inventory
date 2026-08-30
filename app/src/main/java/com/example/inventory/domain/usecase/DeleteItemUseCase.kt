package com.example.inventory.domain.usecase

import com.example.inventory.domain.model.Item
import com.example.inventory.domain.repository.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item: Item){
        repository.deleteItem(item)
    }
}