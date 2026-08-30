package com.example.inventory.domain.usecase

import com.example.inventory.domain.model.Item
import com.example.inventory.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
){
    suspend operator fun invoke(): List<Item> {
        return repository.getItems()
    }
}