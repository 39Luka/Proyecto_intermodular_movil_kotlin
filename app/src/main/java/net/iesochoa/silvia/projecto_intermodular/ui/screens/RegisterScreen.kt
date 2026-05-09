package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.RegisterUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.SimpleInput
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val isValid = uiState.username.isNotBlank() &&
        uiState.email.isNotBlank() &&
        uiState.password.isNotBlank() &&
        uiState.confirmPassword.isNotBlank() &&
        !uiState.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Primary100)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.croissant),
                    contentDescription = "Logo",
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Crear cuenta",
                    style = AppTypography.headlineMedium,
                    color = Secondary600
                )
                Text(
                    text = "Regístrate para comprar, guardar pedidos y aplicar promociones.",
                    style = AppTypography.bodySmall,
                    color = Primary500
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Neutral100)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        SimpleInput(
                            label = "Nombre de usuario",
                            value = uiState.username,
                            onValueChange = onUsernameChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SimpleInput(
                            label = "Email",
                            value = uiState.email,
                            onValueChange = onEmailChange,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SimpleInput(
                            label = "Contraseña",
                            value = uiState.password,
                            onValueChange = onPasswordChange,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            enabled = !uiState.isLoading
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SimpleInput(
                            label = "Repite contraseña",
                            value = uiState.confirmPassword,
                            onValueChange = onConfirmPasswordChange,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            enabled = !uiState.isLoading
                        )

                        if (uiState.errorMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.errorMessage,
                                style = AppTypography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = Error600
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Primary500)
                            }
                        } else {
                            PrimaryButton(
                                text = "Crear cuenta",
                                onClick = onRegisterClick,
                                enabled = isValid,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "¿Ya tienes cuenta? ",
                        style = AppTypography.bodySmall,
                        color = Secondary500
                    )
                    Text(
                        text = "Inicia sesión",
                        style = AppTypography.bodySmall.copy(color = Primary600),
                        modifier = Modifier.clickable(enabled = !uiState.isLoading) { onLoginClick() }
                    )
                }
            }
        }
    }
}
