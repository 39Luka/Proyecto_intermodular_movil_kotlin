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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BackgroundColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BorderColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500
data class HorizontalCardItem(
    val title: String,
    val description: String = "",
    val leftLabel: String? = null,
    val leftValue: String? = null,
    val rightLabel: String? = null,
    val rightValue: String? = null,
    val imageRes: Int? = null
)


@Composable
fun HorizontalCard(
    title: String,
    description: String? = null,
    leftLabel: String? = null,
    leftValue: String? = null,
    rightLabel: String? = null,
    rightValue: String? = null,
    imageRes: Int? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BackgroundColor,
    cornerRadius: Dp = 12.dp // mismo que CustomCard
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp) // altura horizontal más reducida que vertical
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(1.dp, BorderColor, RoundedCornerShape(cornerRadius))
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        // Imagen opcional
        if (imageRes != null) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(cornerRadius))
                    .padding(end = 16.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            // Título
            Text(
                text = title,
                style = AppTypography.bodyMedium,
                color = Secondary500
            )

            // Descripción opcional
            description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = AppTypography.bodySmall,
                    color = Secondary500.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Info inferior opcional
            if ((leftLabel != null && leftValue != null) || (rightLabel != null && rightValue != null)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (leftLabel != null && leftValue != null) {
                        Column {
                            Text(
                                text = leftLabel,
                                style = AppTypography.labelMedium,
                                color = Secondary500.copy(alpha = 0.6f)
                            )
                            Text(
                                text = leftValue,
                                style = AppTypography.bodyMedium,
                                color = Secondary500
                            )
                        }
                    }

                    if (rightLabel != null && rightValue != null) {
                        Column {
                            Text(
                                text = rightLabel,
                                style = AppTypography.labelMedium,
                                color = Secondary500.copy(alpha = 0.6f)
                            )
                            Text(
                                text = rightValue,
                                style = AppTypography.bodyMedium,
                                color = Secondary500
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalCardList(
    items: List<HorizontalCardItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp) // separación vertical entre tarjetas
    ) {
        items.forEach { item ->
            HorizontalCard(
                title = item.title,
                description = item.description.takeIf { it.isNotEmpty() },
                leftLabel = item.leftLabel,
                leftValue = item.leftValue,
                rightLabel = item.rightLabel,
                rightValue = item.rightValue,
                imageRes = item.imageRes,
                modifier = Modifier.fillMaxWidth() // ocupa todo el ancho de la pantalla
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalCardPreview() {
    HorizontalCard(
        title = "Pan de molde",
        description = "Delicioso pan recién hecho",
        leftLabel = "Stock",
        leftValue = "8",
        rightLabel = "Precio",
        rightValue = "0,75€",
        imageRes = android.R.drawable.ic_menu_camera
    )
}
