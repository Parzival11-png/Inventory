package com.example.inventory.ui.createItem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.domain.model.Item
import com.example.inventory.domain.usecase.CreateItemUseCase
import com.example.inventory.domain.usecase.DeleteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateItemViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val createItemUseCase: CreateItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
): ViewModel(){




    fun createItem(item: Item){
        val imagePath = item.
        viewModelScope.launch {
            createItemUseCase(
                Item(

                )
            )
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch {
            deleteItemUseCase(item)
        }
    }
}