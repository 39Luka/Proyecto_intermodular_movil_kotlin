package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class PurchasesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * CP-36: purchasesScreen_displaysEmptyState_whenNoPurchases
     * Verifica que si el usuario no tiene compras, se muestre un mensaje informativo en lugar de una lista vacía.
     */
    @Test
    fun purchasesScreen_displaysEmptyState_whenNoPurchases() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                PurchasesScreen(
                    uiState = PurchasesUiState(pedidos = emptyList(), isLoading = false),
                    onDateRangeSelected = { _, _ -> },
                    onPurchaseClick = {},
                    onBackClick = {},
                    onProfileClick = {},
                    onNextPage = {},
                    onPreviousPage = {}
                )
            }
        }
        
        composeTestRule.onNodeWithText("Aún no tienes compras.").assertIsDisplayed()
    }
}
