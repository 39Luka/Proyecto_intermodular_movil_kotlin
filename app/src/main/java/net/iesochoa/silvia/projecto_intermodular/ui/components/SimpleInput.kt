package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun SimpleInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = modifier) {

        // Label encima con tipografía de heading y color lila
        Text(
            text = label,
            style = AppTypography.bodySmall,
            color = Primary600
        )

        // Campo de texto
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true,
            textStyle = AppTypography.bodyMedium.copy(color = Secondary500),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = Primary600,
                focusedBorderColor = Primary400,
                unfocusedBorderColor = BorderColor,
                focusedContainerColor = Neutral100,
                unfocusedContainerColor = Neutral100,
                disabledContainerColor = Neutral100,
                disabledBorderColor = Neutral200,
                disabledTextColor = TextPrimary.copy(alpha = 0.5f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleInputPreview() {
    var text by remember { mutableStateOf("") }

    SimpleInput(
        label = "Contraseña",
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
    )
}
