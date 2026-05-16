package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography

/**
 * Pantalla que muestra el catálogo completo de productos.
 * Permite filtrar por categorías, realizar búsquedas y navegar de forma paginada.
 */
@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onSearchQueryChange: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    onFilterClick: () -> Unit,
    onCategorySelect: (Int?) -> Unit,
    onDismissFilter: () -> Unit
) {
    val listState = rememberLazyListState()

    // Scroll al principio al cambiar de página
    LaunchedEffect(uiState.currentPage) {
        listState.animateScrollToItem(0)
    }

    if (uiState.showFilterDialog) {
        CategoryFilterDialog(
            categories = uiState.categories,
            selectedCategoryId = uiState.selectedCategoryId,
            onCategorySelected = onCategorySelect,
            onDismiss = onDismissFilter
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp)
    ) {
        item {
            ScreenHeader(
                title = stringResource(R.string.catalog_title),
                showSearch = true,
                showFilter = true,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchQueryChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick,
                onFilterClick = onFilterClick,
                profileImage = uiState.userProfileImage
            )
        }

        item {
            PageIntro(
                eyebrow = stringResource(R.string.catalog_eyebrow),
                title = stringResource(R.string.catalog_intro_title),
                description = stringResource(R.string.catalog_intro_desc)
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

        if (!uiState.isLoading && uiState.filteredProducts.isEmpty() && uiState.error == null) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.catalog_no_results),
                        style = AppTypography.bodyMedium,
                        color = net.iesochoa.silvia.projecto_intermodular.ui.theme.TextPrimary.copy(alpha = 0.6f)
                    )
                }
            }
        }

        item {
            CardList(
                items = uiState.filteredProducts,
                onItemClick = { onProductClick(it.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            PaginationControls(
                currentPage = uiState.currentPage,
                totalPages = uiState.totalPages,
                onPreviousClick = onPreviousPage,
                onNextClick = onNextPage,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
