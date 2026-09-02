package com.example.inventory.ui.home

import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import com.example.inventory.data.processing.BgRemover
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.inventory.domain.model.Item
import com.example.inventory.domain.usecase.GetItemsUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getItemsUseCase: GetItemsUseCase

) : ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

//    fun onAction(action: HomeAction){
//        when(action){
//            HomeAction.LoadInventory -> loadInventory()
//        }
//    }

    init {
        loadInventory()
    }


    fun loadInventory(){
        Log.d("Home", "Loading inventory")
        viewModelScope.launch {
            val inventory = getItemsUseCase()
            _uiState.update {
                it.copy(
                    inventoryList = inventory
                )
            }
        }
    }


}