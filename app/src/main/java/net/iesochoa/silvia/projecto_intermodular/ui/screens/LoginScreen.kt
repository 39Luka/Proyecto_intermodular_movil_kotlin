package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
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
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.SimpleInput
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary600
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🔹 Logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // reemplaza con tu logo
            contentDescription = "Logo",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        // 🔹 Título
        Text(
            text = "Inicio sesión",
            style = AppTypography.headlineSmall,
            color = Secondary500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 🔹 Inputs
        SimpleInput(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        SimpleInput(
            label = "Contraseña",
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Botón de login
        PrimaryButton(
            text = "Iniciar sesión",
            onClick = { onLoginClick(email, password) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Link de registro
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                style = AppTypography.bodySmall,
                color = Secondary500
            )
            ClickableText(
                text = AnnotatedString("Regístrate"),
                onClick = { onRegisterClick() },
                style = AppTypography.bodySmall.copy(color = Primary600)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
