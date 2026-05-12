package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500

@Composable
fun PurchasesScreen(
    uiState: PurchasesUiState,
    onSearchChange: (String) -> Unit,
    onPurchaseClick: (Int) -> Unit,
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
                title = "Mis compras",
                showSearch = true,
                showFilter = false,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick,
                profileImage = uiState.userProfileImage
            )
        }

        item {
            PageIntro(
                eyebrow = "Historial",
                title = "Tus pedidos ordenados.",
                description = "Estado, importe y acceso directo al detalle de cada compra sin apariencia de listado improvisado."
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
            PedidoList(
                pedidos = uiState.pedidos,
                onItemClick = { onPurchaseClick(it.id) }
            )
        }
    }
}
