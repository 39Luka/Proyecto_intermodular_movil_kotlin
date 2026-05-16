package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Barra de búsqueda personalizada y accesible.
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BackgroundColor,
    borderColor: Color = BorderColor,
    cornerRadius: Dp = 12.dp,
    textColor: Color = Secondary500,
    placeholderText: String = "Buscar",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            enabled = enabled,
            readOnly = readOnly,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = AppTypography.bodyMedium,
                    color = textColor.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null, // Decorativo
                    tint = textColor.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(cornerRadius),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            textStyle = AppTypography.bodyMedium.copy(color = textColor),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                disabledTextColor = textColor,
                cursorColor = textColor,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                disabledBorderColor = borderColor,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                disabledContainerColor = backgroundColor
            )
        )

        // Capa de clic accesible
        if (onClick != null) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .semantics { role = Role.Button }
                    .clickable(
                        onClickLabel = "Abrir opciones de búsqueda",
                        onClick = onClick
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarWithIconPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        SearchBar(
            query = "Pan",
            onQueryChange = { /* preview */ }
        )
    }
}
