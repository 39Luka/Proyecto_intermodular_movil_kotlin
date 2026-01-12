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
@Composable
fun CustomCard(
    imageRes: Int,
    title: String,
    bottomText1: String?, // 🔹 opcional
    bottomText2: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Título izquierda
        Text(
            text = title,
            style = AppTypography.bodyMedium,
            color = Secondary500,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Textos derecha
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            // 🔹 Texto opcional y tachado
            if (!bottomText1.isNullOrBlank()) {
                Text(
                    text = bottomText1,
                    style = AppTypography.bodySmall, // más pequeño
                    color = Secondary500.copy(alpha = 0.7f), // opcional pero recomendado
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    textAlign = TextAlign.End
                )
            }

            // 🔹 Texto principal
            Text(
                text = bottomText2,
                style = AppTypography.bodyMedium,
                color = Secondary500,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
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
