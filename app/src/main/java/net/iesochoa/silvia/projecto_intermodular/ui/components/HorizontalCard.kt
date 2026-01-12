package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BackgroundColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BorderColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500

@Composable
fun HorizontalCard(
    title: String,
    description: String,
    price: String,
    stock: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BackgroundColor,
    borderColor: Color = BorderColor,
    cornerRadius: Dp = 12.dp
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = AppTypography.bodyMedium,
            color = Secondary500
        )
        Text(
            text = description,
            style = AppTypography.bodySmall,
            color = Secondary500.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Precio: $price",
                style = AppTypography.bodyMedium,
                color = Secondary500
            )
            Text(
                text = "Stock: $stock",
                style = AppTypography.bodyMedium,
                color = Secondary500
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
        price = "0,75€",
        stock = 8
    )
}