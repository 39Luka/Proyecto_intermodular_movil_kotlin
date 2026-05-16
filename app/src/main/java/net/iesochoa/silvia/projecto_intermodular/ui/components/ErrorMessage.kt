package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Componente para mostrar mensajes de error de forma visualmente destacada.
 * Utiliza un fondo rojizo suave y permite incluir una acción de reintento.
 *
 * @param message El mensaje de error a mostrar.
 * @param onRetry Callback opcional para reintentar la operación fallida.
 * @param modifier Modificador para el contenedor externo.
 */
@Composable
fun ErrorMessage(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Error100, RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = message,
                color = Error600,
                style = AppTypography.bodyMedium
            )
            
            if (onRetry != null) {
                Text(
                    text = "REINTENTAR",
                    color = Error600,
                    style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { onRetry() }
                )
            }
        }
    }
}
