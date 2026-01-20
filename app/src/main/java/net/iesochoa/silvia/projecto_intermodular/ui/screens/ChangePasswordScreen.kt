package net.iesochoa.silvia.projecto_intermodular.ui.screens

import SimpleInput
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader

@Composable
fun ChangePasswordScreen(
    uiState: ProfileUiState,
    onNewPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: (() -> Unit)?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp)
    ) {
        // 🔹 Header
        item {
            ScreenHeader(
                title = uiState.username,
                onBackClick = onBackClick
            )
        }

        // 🔹 Contenido principal
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SimpleInput(
                    label = "Nueva contraseña",
                    value = uiState.newPassword,
                    onValueChange = onNewPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                SimpleInput(
                    label = "Repetir contraseña",
                    value = uiState.repeatPassword,
                    onValueChange = onRepeatPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                PrimaryButton(
                    text = "Guardar",
                    onClick = onSaveClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

