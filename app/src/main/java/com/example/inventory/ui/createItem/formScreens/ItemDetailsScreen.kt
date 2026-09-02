package com.example.inventory.ui.createItem.formScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.inventory.ui.createItem.CreateAction
import com.example.inventory.ui.createItem.CreateItemViewModel
import com.example.inventory.ui.createItem.CreateUiState

@Composable
fun ItemDetailsScreen(
    uiState: CreateUiState,
    onAction: (CreateAction) -> Unit,
    onCreateClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.matchParentSize(),
                    model = uiState.resultImage,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo NAME
            Text(
                text = "NAME",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.currentName,
                onValueChange = { onAction(CreateAction.OnNameChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Enter item name...") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo DESCRIPTION
            Text(
                text = "DESCRIPTION",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.currentDescription,
                onValueChange = { onAction(CreateAction.OnDescriptionChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Enter item description...") }
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Botón CREATE
            Button(
                onClick = {
                    onAction(CreateAction.CreateItem)
                    onCreateClick()
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text("CREATE", fontSize = 16.sp)
            }
        }
    }
}