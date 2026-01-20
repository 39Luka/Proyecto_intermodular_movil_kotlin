package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchChange: (String) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {

        item {
            ScreenHeader(
                title = "Bienvenido Usuario",
                showSearch = true,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick
            )
        }

        // 🔹 Últimas promociones
        item {
            Text(
                text = "Últimas promociones",
                style = AppTypography.bodyMedium,
                color = Secondary600
            )
        }

        // 🔹 GRID PRODUCTOS filtrados
        item {
            CardList(
                items = uiState.filteredPromociones,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Top Ventas",
                style = AppTypography.bodyMedium,
                color = Secondary600
            )
        }

        item {
            CardList(
                items = uiState.filteredTopVentas,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}
