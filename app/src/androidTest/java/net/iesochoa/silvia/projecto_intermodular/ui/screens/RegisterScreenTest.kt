package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import net.iesochoa.silvia.projecto_intermodular.model.RegisterUiState
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_displaysFields() {
        composeTestRule.setContent {
            Projecto_IntermodularTheme {
                RegisterScreen(
                    uiState = RegisterUiState(),
                    onEmailChange = {},
                    onPasswordChange = {},
                    onConfirmPasswordChange = {},
                    onRegisterClick = {},
                    onLoginClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("CORREO ELECTRÓNICO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONTRASEÑA").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIRMAR CONTRASEÑA").assertIsDisplayed()
    }
}
