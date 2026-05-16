package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.OffersUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.TextPrimary

/**
 * Pantalla que muestra las promociones y ofertas activas.
 * Los productos con descuento se presentan en tarjetas horizontales para facilitar
 * la lectura de las condiciones de la oferta.
 */
@Composable
fun OffersScreen(
    uiState: OffersUiState,
    onSearchChange: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit
) {
    val listState = rememberLazyListState()

    // Scroll al principio al cambiar de página
    LaunchedEffect(uiState.currentPage) {
        listState.animateScrollToItem(0)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp)
    ) {

        item {
            ScreenHeader(
                title = stringResource(R.string.offers_title),
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
                eyebrow = stringResource(R.string.offers_eyebrow),
                title = stringResource(R.string.offers_intro_title),
                description = stringResource(R.string.offers_intro_desc)
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
                        text = stringResource(R.string.offers_no_results),
                        style = AppTypography.bodyMedium,
                        color = TextPrimary.copy(alpha = 0.6f)
                    )
                }
            }
        }

        item {
            HorizontalCardList(
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
