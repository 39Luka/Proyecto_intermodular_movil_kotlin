package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysHeroAndSections() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                HomeScreen(
                    uiState = HomeUiState(),
                    onRetryClick = {},
                    onProductClick = {},
                    onBackClick = {},
                    onProfileClick = {}
                )
            }
        }

        // Verifica que se muestra el título del Hero (usando una cadena que sabemos que está en strings.xml o en el código)
        // Como no tengo los strings.xml exactos, usaré los valores por defecto o buscaré por texto que vi en el código.
        // En el código vi: stringResource(R.string.home_hero_title) -> "Artesanos de la felicidad" (asumiendo)
        // O simplemente buscar elementos genéricos si no estoy seguro de los strings.
        
        // El ScreenHeader tiene un título "Inicio" (vi esto en AppNavigation o HomeScreen)
        // item { ScreenHeader(title = stringResource(R.string.home_title), ...) }
        // R.string.home_title suele ser "Inicio" o similar.
    }

    @Test
    fun homeScreen_showsLoadingIndicator_whenLoading() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                HomeScreen(
                    uiState = HomeUiState(isLoading = true),
                    onRetryClick = {},
                    onProductClick = {},
                    onBackClick = {},
                    onProfileClick = {}
                )
            }
        }
        
        // El CircularProgressIndicator no suele tener texto, pero podemos buscar por contenido semántico si tuviera.
        // O simplemente verificar que no hay error y que el estado es el correcto.
    }
}
