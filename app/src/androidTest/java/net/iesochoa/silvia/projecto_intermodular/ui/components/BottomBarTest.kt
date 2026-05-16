package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.Screen
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class BottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomBar_displaysAllDestinations() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            Projecto_IntermodularTheme {
                BottomBar(navController = navController)
            }
        }

        composeTestRule.onNodeWithContentDescription(Screen.Home.route).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(Screen.Catalog.route).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(Screen.Offers.route).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(Screen.Purchases.route).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(Screen.Cart.route).assertIsDisplayed()
    }
}
