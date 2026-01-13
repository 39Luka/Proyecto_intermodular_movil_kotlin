package net.iesochoa.silvia.projecto_intermodular.ui.screens

import SimpleInput
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import net.iesochoa.silvia.projecto_intermodular.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
   viewModel: RegisterViewModel = viewModel(),
   onRegisterSuccess: () -> Unit,
   onLoginClick: () -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🔹 Logo
        Image(
            painter = painterResource(id = R.drawable.croissant),
            contentDescription = "Logo",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        // 🔹 Título
        Text(
            text = "Registrarse",
            style = AppTypography.headlineMedium,
            color = Secondary600,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 🔹 Inputs
        SimpleInput(
            label = "Nombre Usuario",
            value = uiState.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SimpleInput(
            label = "Email",
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SimpleInput(
            label = "Contraseña",
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SimpleInput(
            label = "Repetir contraseña",
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔹 Mensaje de error
        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                style = AppTypography.bodySmall,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // 🔹 Botón de registro
        PrimaryButton(
            text = "Registrar",
            onClick = {
                viewModel.register { onRegisterSuccess() }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Link a login
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
                text = "Iniciar sesión",
                style = AppTypography.bodySmall.copy(color = Primary600),
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

