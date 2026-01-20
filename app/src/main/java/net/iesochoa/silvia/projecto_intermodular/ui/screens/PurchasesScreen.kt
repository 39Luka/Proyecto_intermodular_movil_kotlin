package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoList
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader

@Composable
fun PurchasesScreen(
    uiState: PurchasesUiState,
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
            PedidoList(pedidos = uiState.pedidos)
        }
    }
}
