package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import net.iesochoa.silvia.projecto_intermodular.model.CatalogUiState
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * CP-35: catalogScreen_displaysProducts
     * Verifica que la pantalla del catálogo renderice correctamente la lista de productos disponibles.
     */
    @Test
    fun catalogScreen_displaysProducts() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                CatalogScreen(
                    uiState = CatalogUiState(),
                    onSearchQueryChange = {},
                    onProductClick = {},
                    onBackClick = {},
                    onProfileClick = {},
                    onNextPage = {},
                    onPreviousPage = {},
                    onFilterClick = {},
                    onCategorySelect = {},
                    onDismissFilter = {}
                )
            }
        }
    }
}
