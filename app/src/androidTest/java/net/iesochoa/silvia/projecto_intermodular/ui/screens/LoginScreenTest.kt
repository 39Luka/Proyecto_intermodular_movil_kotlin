package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import net.iesochoa.silvia.projecto_intermodular.model.LoginUiState
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginButton_isDisabled_whenFieldsAreEmpty() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                LoginScreen(
                    uiState = LoginUiState(email = "", password = ""),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onLoginClick = {},
                    onRegisterClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsNotEnabled()
    }

    @Test
    fun loginButton_isEnabled_whenFieldsAreNotEmpty() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                LoginScreen(
                    uiState = LoginUiState(email = "test@example.com", password = "password"),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onLoginClick = {},
                    onRegisterClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").assertIsEnabled()
    }

    @Test
    fun loginClick_triggersCallback() {
        var loginClicked = false
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                LoginScreen(
                    uiState = LoginUiState(email = "test@example.com", password = "password"),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onLoginClick = { loginClicked = true },
                    onRegisterClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Iniciar Sesión").performClick()
        assert(loginClicked)
    }
}
