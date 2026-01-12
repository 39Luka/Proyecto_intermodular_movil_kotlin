package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun SimpleInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        // Label encima con tipografía de heading y color lila
        Text(
            text = label,
            style = AppTypography.headlineSmall,
            color = Secondary500
        )

        // Campo de texto
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true,
            textStyle = AppTypography.bodyMedium.copy(color = Secondary500),
            colors = OutlinedTextFieldDefaults.colors( // Cambio clave
                focusedTextColor = Secondary500,
                unfocusedTextColor = Secondary500,
                cursorColor = Secondary500,
                focusedBorderColor = BorderColor,
                unfocusedBorderColor = BorderColor,
                focusedContainerColor = BackgroundColor,
                unfocusedContainerColor = BackgroundColor,
                focusedLabelColor = Secondary500,
                unfocusedLabelColor = Secondary500
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleInputPreview() {
    var text by remember { mutableStateOf("") }

    SimpleInput(
        label = "Mariano",
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
