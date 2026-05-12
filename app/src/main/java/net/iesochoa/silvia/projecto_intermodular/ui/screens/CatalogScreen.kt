package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onSearchQueryChange: (String) -> Unit,
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
                title = "Catálogo",
                showSearch = true,
                showFilter = true,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchQueryChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick,
                profileImage = uiState.userProfileImage
            )
        }

        item {
            PageIntro(
                eyebrow = "Catálogo",
                title = "El mostrador completo.",
                description = "Nuestra selección completa de bollería, pan y café organizada para ti."
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
            CardList(
                items = uiState.filteredProducts,
                onItemClick = { onProductClick(it.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
