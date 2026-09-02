package com.example.inventory.ui.createItem

import android.net.Uri


interface CreateAction {


    data object CreateItem : CreateAction
    data class ProcessImage(val px : Int) : CreateAction
    data class OnNameChange(val nName : String) : CreateAction
    data class OnDescriptionChange(val nDescription : String) : CreateAction
    data class SetCurrentPhoto(val uri : Uri) : CreateAction
}