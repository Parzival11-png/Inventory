package com.example.inventory.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.inventory.R

// Usaremos un estilo pixelado simulado para la fuente y los elementos de UI.
val pixelFont = FontFamily.Monospace // Reemplazar con una fuente pixel art real si está disponible.
val darkBackgroundColor = Color(0xFF1E1E2C)
val darkerBackgroundColor = Color(0xFF14141A)
val primaryColor = Color(0xFFBB86FC)
val secondaryColor = Color(0xFF03DAC6)
val darkGrayColor = Color(0xFF33333E)
val accentRedColor = Color(0xFFB00020)
val textPrimaryColor = Color(0xFFE1E1E1)

@Composable
fun PixelArtItemCard(
    itemImage: Int,
    itemName: String,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.85f), // Mantener la proporción de la tarjeta del diseño
        shape = RoundedCornerShape(4.dp), // Esquinas ligeramente redondeadas para el estilo pixelado
        colors = CardDefaults.cardColors(containerColor = darkerBackgroundColor),
        border = BorderStroke(2.dp, darkGrayColor) // Borde para el efecto de "recuadro"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar
        ) {
            // Contenedor de la imagen con fondo pixelado a cuadros (simulado)
            Box(
                modifier = Modifier
                    .weight(0.7f) // Usar peso para la imagen principal
                    .padding(8.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f), // Imagen cuadrada
                contentAlignment = Alignment.Center
            ) {
                // Simulación de fondo a cuadros para el arte pixelado
                // Puedes usar una imagen de fondo o un canvas personalizado para esto.
                // Aquí solo usaremos un color de fondo diferente por ahora.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, darkGrayColor, RoundedCornerShape(2.dp))
                )

                AsyncImage(
                    model = itemImage,
                    contentDescription = itemName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(16.dp)
                )

                // Mostrar cantidad si está presente (como en la captura: x5, x1)
/*                itemQuantity?.let {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp)
                            .border(1.dp, Color.White) // Borde para que destaque
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "x$it",
                            fontFamily = pixelFont,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }*/
            }

            // OBJ TAG
            Text(
                text = itemName.uppercase(),
                color = textPrimaryColor,
                fontFamily = pixelFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
            )

            // OBJ FUNCS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // WATCH
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .clickable(onClick = { })
                        .background(darkBackgroundColor, RoundedCornerShape(2.dp))
                        .border(1.dp, darkGrayColor, RoundedCornerShape(2.dp))
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "VER",
                        color = Color.White,
                        fontFamily = pixelFont,
                        fontSize = 12.sp
                    )
                }

                // DROP
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .clickable(onClick = { })
                        .background(accentRedColor, RoundedCornerShape(2.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(2.dp))
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "TIRAR",
                        color = Color.White,
                        fontFamily = pixelFont,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Chamoy")
@Composable
fun Prev(modifier: Modifier = Modifier) {
    InventoryPlus()
}


@Composable
fun ItemCard(
    image : Int
) {
    Box(
        modifier = Modifier.size(165.dp, 205.dp)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(160.dp, 200.dp)
                .background(color = Color.Gray)
        ){
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.frame), // Carga el bitmap puro
                modifier = Modifier.matchParentSize(),
                filterQuality = FilterQuality.None, // Bloquea el suavizado
                contentScale = ContentScale.Fit, // Estira la textura
                contentDescription = "Frame"
            )
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .offset(x = 35.dp, y = 30.dp) // Lo desplaza sin tocar sus dimensiones
            ) {
                AsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
            Row(
                modifier = Modifier.fillMaxSize()
                    .offset(y = (-15).dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                CardButton(
                    R.drawable.button,{}
                )
                CardButton(
                    R.drawable.button2,{}
                )
            }
        }
    }

}

@Composable
fun InventoryPlus(

) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemCard(R.drawable.waifu_im_4548)
            ItemCard(R.drawable.rei)
            ItemCard(R.drawable.waifu_im_4548)
            ItemCard(R.drawable.rei)
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemCard(R.drawable.waifu_im_4548)
            ItemCard(R.drawable.rei)
            ItemCard(R.drawable.waifu_im_4548)
            ItemCard(R.drawable.rei)
        }

    }
}


@Composable
fun CardButton(
    image: Int,
    onPressed : () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(60.dp, 30.dp)
            .clickable(
                onClick = onPressed
            )
    ){
        Image(
            bitmap = ImageBitmap.imageResource(id = image), // Carga el bitmap puro
            modifier = Modifier.matchParentSize(),
            filterQuality = FilterQuality.None, // Bloquea el suavizado
            contentScale = ContentScale.Fit, // Estira la textura
            contentDescription = "Button"
        )
    }
}