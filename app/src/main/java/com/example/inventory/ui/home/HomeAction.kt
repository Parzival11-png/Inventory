package com.example.inventory.ui.home

import android.net.Uri
import androidx.compose.ui.unit.IntSize

sealed interface HomeAction{

    data object LoadInventory : HomeAction

}