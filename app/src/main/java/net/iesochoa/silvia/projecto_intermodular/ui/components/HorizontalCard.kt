package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BackgroundColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500

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
    cornerRadius: Dp = 0.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius))
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

            // Info inferior opcional (Left/Right)
            if ((leftLabel != null && leftValue != null) || (rightLabel != null && rightValue != null)) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Left info
                    if (leftLabel != null && leftValue != null) {
                        Column {
                            Text(
                                text = leftLabel,
                                style = AppTypography.labelMedium,
                                color = Secondary500.copy(alpha = 0.6f) // más transparente que la descripción
                            )
                            Text(
                                text = leftValue,
                                style = AppTypography.bodyMedium,
                                color = Secondary500
                            )
                        }
                    }

                    // Right info
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
        imageRes = null
    )
}
