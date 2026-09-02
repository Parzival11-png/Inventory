package com.example.inventory.ui.createItem.formScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.inventory.ui.createItem.CreateItemViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.example.inventory.ui.createItem.CreateAction
import com.example.inventory.ui.createItem.CreateUiState

@Composable
fun ImagePreviewScreen(
    uiState: CreateUiState,
    onAction: (CreateAction) -> Unit,
    onContinueClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.selectedPhotoUri == null){
            Text(
                text = "No Photo Selected",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }else{
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.matchParentSize(),
                    model = uiState.selectedPhotoUri,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }

        if (uiState.selectedPhotoUri != null){
            Row() {
                Button(
                    onClick = { onAction(CreateAction.ProcessImage(32)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text("16px")
                }
                Button(
                    onClick = { onAction(CreateAction.ProcessImage(64)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text("32px")
                }
                Button(
                    onClick = {
                        if (uiState.resultImage != null){
                            onContinueClick()
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Continue ${uiState.currentPixelRes}px",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!uiState.processingImage){
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = uiState.resultImage,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }else{
                CircularProgressIndicator()
            }

        }

    }

}