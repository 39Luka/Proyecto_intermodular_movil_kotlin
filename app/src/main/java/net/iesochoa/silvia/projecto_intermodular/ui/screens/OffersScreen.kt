package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader

@Composable
fun OffersScreen(
    uiState: OffersUiState,
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
                showSearch = true,
                showFilter = true,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick
            )
        }

        item {
            HorizontalCardList(
                items = uiState.filteredProducts,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

