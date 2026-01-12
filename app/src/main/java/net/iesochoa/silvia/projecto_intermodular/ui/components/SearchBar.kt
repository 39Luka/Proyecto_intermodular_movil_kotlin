package net.iesochoa.silvia.projecto_intermodular.ui.components


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BackgroundColor,
    borderColor: Color = BorderColor,
    cornerRadius: Dp = 12.dp,
    textColor: Color = Secondary500,
    placeholderText: String = "Buscar productos..."
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = placeholderText,
                style = AppTypography.bodyMedium,
                color = textColor.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                // Aquí se usa Icons.Default.Search que viene de material3-icons-core
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = textColor.copy(alpha = 0.7f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = AppTypography.bodyMedium.copy(color = textColor),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor
        )
    )
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