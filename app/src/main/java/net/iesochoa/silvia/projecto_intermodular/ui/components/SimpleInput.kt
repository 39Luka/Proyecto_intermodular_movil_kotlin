package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Componente de entrada de texto personalizado con una etiqueta superior.
 * Estilizado según el tema de la aplicación con bordes redondeados y colores lila/secundario.
 *
 * @param label Texto descriptivo que aparece sobre el campo.
 * @param value Valor actual del texto.
 * @param onValueChange Callback que se dispara al cambiar el texto.
 * @param modifier Modificador para personalizar el layout.
 * @param enabled Indica si el campo es editable.
 * @param isPasswordField Indica si es un campo de contraseña (habilita el icono del ojo).
 * @param keyboardOptions Opciones del teclado (tipo de entrada, acciones, etc.).
 */
@Composable
fun SimpleInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPasswordField: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }

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
            visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            trailingIcon = if (isPasswordField) {
                {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description, tint = Primary500)
                    }
                }
            } else null,
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
        isPasswordField = true
    )
}
