package net.iesochoa.silvia.projecto_intermodular.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            Projecto_IntermodularTheme {
                AppNavigation(navController = navController)
            }
        }
    }

    /**
     * CP-21: appNavHost_verifyStartDestination
     * Verifica que al iniciar la aplicación, la pantalla de destino inicial sea la de Inicio de Sesión (Login).
     */
    @Test
    fun appNavHost_verifyStartDestination() {
        assertEquals(Screen.Login.route, navController.currentBackStackEntry?.destination?.route)
    }

    /**
     * CP-21.1: appNavHost_clickRegister_navigatesToRegister
     * Comprueba que al pulsar el botón de "Crea una cuenta" el controlador de navegación cambie a la pantalla de Registro.
     */
    @Test
    fun appNavHost_clickRegister_navigatesToRegister() {
        composeTestRule.onNodeWithText("Crea una cuenta").performClick()
        assertEquals(Screen.Register.route, navController.currentBackStackEntry?.destination?.route)
    }
}
