package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.ui.graphics.Color.Companion.White


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()             // ocupa todo el ancho posible
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary600,
            contentColor = White
        )
    ) {
        Text(
            text = text,
            style = AppTypography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp) // margen interno extra
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Enviar",
        onClick = { /* Acción */ }
    )
}
