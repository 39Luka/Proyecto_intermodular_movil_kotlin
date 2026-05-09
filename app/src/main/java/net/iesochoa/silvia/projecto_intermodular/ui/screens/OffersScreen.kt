package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500

@Composable
fun OffersScreen(
    uiState: OffersUiState,
    onSearchChange: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp)
    ) {

        item {
            ScreenHeader(
                title = "Promociones",
                showSearch = true,
                showFilter = false,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick
            )
        }

        item {
            PageIntro(
                eyebrow = "Ofertas activas",
                title = "Promociones con mejor contexto.",
                description = "Cada oferta se presenta como una oportunidad real de compra con lectura clara y ritmo visual."
            )
        }

        if (uiState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary500)
                }
            }
        }

        if (uiState.error != null) {
            item {
                ErrorMessage(message = uiState.error)
            }
        }

        item {
            HorizontalCardList(
                items = uiState.filteredProducts,
                onItemClick = { onProductClick(it.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
