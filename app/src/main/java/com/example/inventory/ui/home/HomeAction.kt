package com.example.inventory.ui.home

import android.net.Uri
import androidx.compose.ui.unit.IntSize

sealed interface HomeAction{

    data object PickPhoto : HomeAction
    data object ProcessImage : HomeAction
    data object AddPixels : HomeAction
    data object SubtractPixels : HomeAction

}