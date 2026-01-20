package net.iesochoa.silvia.projecto_intermodular.ui.screens

import SimpleInput
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.LoginUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary600
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600
import net.iesochoa.silvia.projecto_intermodular.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.croissant),
            contentDescription = "Logo",
            modifier = Modifier.size(64.dp).padding(bottom = 16.dp)
        )

        Text(
            text = "Inicio de sesión",
            style = AppTypography.headlineMedium,
            color = Secondary600,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SimpleInput(
            label = "Email",
            value = uiState.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SimpleInput(
            label = "Contraseña",
            value = uiState.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                style = AppTypography.bodySmall,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        PrimaryButton(
            text = "Iniciar sesión",
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                style = AppTypography.bodySmall,
                color = Secondary500
            )
            Text(
                text = "Regístrate",
                style = AppTypography.bodySmall.copy(color = Primary600),
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}


