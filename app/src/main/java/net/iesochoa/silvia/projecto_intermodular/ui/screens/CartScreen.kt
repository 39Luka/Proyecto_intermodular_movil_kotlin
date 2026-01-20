package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.CartUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.components.SelectorOfertas
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600

@Composable
fun CartScreen(
    uiState: CartUiState,
    onOfertaSeleccionada: (Oferta) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {

        item {
            ScreenHeader(onBackClick = onBackClick,
                onProfileClick = onProfileClick)
        }

        item {
            HorizontalCardList(
                items = uiState.products,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Aplicar promociones",
                style = AppTypography.titleMedium,
                color = Secondary600
            )
        }

        item {
            SelectorOfertas(
                ofertas = uiState.ofertasDisponibles,
                onSeleccionCambio = { onOfertaSeleccionada(it) }
            )
        }

        item {
            Text(
                text = "Ofertas seleccionadas: ${uiState.ofertasSeleccionadas.joinToString()}",
                style = AppTypography.bodyMedium
            )
        }
    }
}

