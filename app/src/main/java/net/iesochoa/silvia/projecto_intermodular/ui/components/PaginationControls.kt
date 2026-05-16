package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Neutral400
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography

/**
 * Controles de navegación para listas paginadas.
 * Muestra flechas de anterior/siguiente y el contador de página actual sobre el total.
 *
 * @param currentPage Índice de la página actual (basado en 0).
 * @param totalPages Cantidad total de páginas disponibles.
 * @param onPreviousClick Acción al pulsar el botón de página anterior.
 * @param onNextClick Acción al pulsar el botón de página siguiente.
 */
@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPreviousClick,
            enabled = currentPage > 0,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Primary500,
                disabledContentColor = Neutral400
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior")
        }

        Text(
            text = "${currentPage + 1} / $totalPages",
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = TextAlign.Center,
            style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = Primary500
        )

        IconButton(
            onClick = onNextClick,
            enabled = currentPage < totalPages - 1,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Primary500,
                disabledContentColor = Neutral400
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
        }
    }
}
