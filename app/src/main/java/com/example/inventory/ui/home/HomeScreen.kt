package com.example.inventory.ui.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.inventory.R
import com.example.inventory.domain.model.Item


@Composable
fun HomeRoute(
    vM : HomeViewModel = hiltViewModel(),
    onCreateNewItemClick : () -> Unit
) {
    val uiState by vM.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        vM.loadInventory()
    }

    HomeContent(
        uiState = uiState,
        goToCreateNewItem = onCreateNewItemClick
    )
}


@Composable
fun HomeContent(
    uiState: HomeUiState,
    goToCreateNewItem : () -> Unit
) {
    Scaffold(

    ) { inner_padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner_padding)
        ) {
            Button(
                onClick = goToCreateNewItem
            ) {
                Text("Go to Create New Item")
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = uiState.inventoryList,
                    key = { item -> item.id } // Optimiza el renderizado identificando cada item
                ) { item ->
                    ItemCard(
                        image = item.imagePath,
                        name = item.name
                    )
                }
            }
        }

    }

}


