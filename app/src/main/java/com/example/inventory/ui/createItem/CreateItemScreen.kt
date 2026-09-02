package com.example.inventory.ui.createItem

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CreateItemRoute(
    vM: CreateItemViewModel = hiltViewModel(),
    onChooseImgButtonClick : () -> Unit
) {

    CreateItemContent()
}

@Composable
fun CreateItemContent(

) {

}