package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

data class CardItem(
    val imageRes: Int,
    val title: String,
    val bottomText1: String?= null,
    val bottomText2: String
)

@Composable
fun CustomCard(
    imageRes: Int,
    title: String,
    bottomText1: String?,
    bottomText2: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp) // 🔒 TODAS MISMO TAMAÑO
            .background(BackgroundColor, RoundedCornerShape(12.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp) // altura fija de imagen
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Título
        Text(
            text = title,
            style = AppTypography.bodyMedium,
            color = Secondary500,
            maxLines = 1
        )

        // 👇 EMPUJA LOS PRECIOS HACIA ABAJO
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            if (!bottomText1.isNullOrBlank()) {
                Text(
                    text = bottomText1,
                    style = AppTypography.bodySmall,
                    color = Secondary500.copy(alpha = 0.7f),
                    textDecoration = TextDecoration.LineThrough,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = bottomText2,
                style = AppTypography.bodyMedium,
                color = Secondary500,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
fun CardList(
    items: List<CardItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val chunkedItems = items.chunked(2)
        chunkedItems.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    CustomCard(
                        imageRes = item.imageRes,
                        title = item.title,
                        bottomText1 = item.bottomText1,
                        bottomText2 = item.bottomText2,
                        modifier = Modifier.weight(1f) // 👈 igual tamaño
                    )
                }

                // Si solo hay 1 tarjeta, rellenamos el hueco con un Spacer
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomCardPreview() {
    CustomCard(
        imageRes = android.R.drawable.ic_menu_camera,
        title = "Título de la tarjeta",
        bottomText1 = "Texto inferior 1",
        bottomText2 = "Texto inferior 2"
    )
}
