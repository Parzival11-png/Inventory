package com.example.inventory.ui.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.Image
import coil3.compose.AsyncImage
import coil3.size.Scale
import com.example.inventory.R
import kotlin.div

@Composable
fun HomeRoute(
    vM : HomeViewModel = hiltViewModel()
) {
    val uiState by vM.uiState.collectAsStateWithLifecycle()


    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        vM.setCurrentPhoto(uri)
    }

    HomeContent(
        uiState = uiState,
        onAction = vM::onAction,
    )
}


@Composable
fun HomeContent(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PixelArtItemCard(
            itemImage = R.drawable.rei,
            itemName = "Rei Chikita",
        )

    }
}
@Composable
fun HomeContent2(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    choosePhoto : () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        verticalArrangement = Arrangement.Center,
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
        Text(
            text = "Res : ${uiState.imageRes}",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Button(
            onClick = { choosePhoto() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E1E1E),
                contentColor = Color(0xFFE0E0E0)
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Choose a Photo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        if (uiState.selectedPhotoUri != null){
            Row() {
                Button(
                    onClick = { onAction(HomeAction.SubtractPixels) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text("-")
                }
                Button(
                    onClick = { onAction(HomeAction.ProcessImage) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Pix ${uiState.pixelRes}px Image",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Button(
                    onClick = { onAction(HomeAction.AddPixels) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1E1E),
                        contentColor = Color(0xFFE0E0E0)
                    ),
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text("+")
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

