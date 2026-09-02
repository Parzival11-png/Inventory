package com.example.inventory.ui.createItem

import android.content.ClipDescription
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.processing.BgRemover
import com.example.inventory.domain.model.Item
import com.example.inventory.domain.model.ItemToSave
import com.example.inventory.domain.usecase.CreateItemUseCase
import com.example.inventory.domain.usecase.DeleteItemUseCase
import com.example.inventory.ui.home.HomeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CreateItemViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,

    private val bgRemover: BgRemover,

    private val createItemUseCase: CreateItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(CreateUiState())
    val uiState = _uiState.asStateFlow()



    fun onAction(action: CreateAction){
        when(action){
            CreateAction.CreateItem -> createItem()
            is CreateAction.ProcessImage -> processImage(action.px)
            is CreateAction.OnNameChange -> onNameChange(action.nName)
            is CreateAction.OnDescriptionChange -> onDescriptionChange(action.nDescription)
            is CreateAction.SetCurrentPhoto -> setCurrentPhoto(action.uri)
        }
    }

    private fun onNameChange(newName: String){
        _uiState.update { it.copy(currentName = newName) }
    }
    private fun onDescriptionChange(newDescription: String){
        _uiState.update { it.copy(currentDescription = newDescription) }
    }


    private fun setCurrentPhoto(photo : Uri){
        Log.d("Home", "Photo selected : $photo")
        _uiState.update {
            it.copy(
                selectedPhotoUri = photo
            )
        }
    }
    private fun processImage(px : Int) {
        Log.d("Home", "Trying to process image")
        val uriPhoto = uiState.value.selectedPhotoUri ?: return
        //val pixels = _uiState.value.pixelRes
        viewModelScope.launch {
            _uiState.update { it.copy(processingImage = true ) }
            val resultImg = withContext(Dispatchers.Default){
                val bitmapPhoto = bgRemover.uriToBitmap(uriPhoto)
                val resultBitmap = bgRemover.removeBg(bitmapPhoto)
                val pixelBitmap = bgRemover.toPixelArt(resultBitmap, px,8)
                pixelBitmap
            }
            _uiState.update {
                it.copy(
                    resultImage = resultImg,
                    processingImage = false
                )
            }
        }
    }




    private fun createItem(){
        with(_uiState.value){
            resultImage?.let { bitmapNotNull ->
                viewModelScope.launch {
                    createItemUseCase(
                        name = currentName,
                        description = currentDescription,
                        bitmapResult = bitmapNotNull
                    )
                }
            }
        }
    }
    private fun deleteItem(item: Item){
        viewModelScope.launch {
            deleteItemUseCase(item)
        }
    }
}