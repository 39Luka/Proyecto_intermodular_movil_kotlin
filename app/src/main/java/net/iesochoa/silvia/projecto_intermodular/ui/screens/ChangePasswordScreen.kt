package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun ChangePasswordScreen(
    uiState: ProfileUiState,
    onCurrentPasswordChange: (String) -> Unit,
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
        item {
            ScreenHeader(
                title = "Seguridad",
                onBackClick = onBackClick
            )
        }

        item {
            PageIntro(
                eyebrow = "Ajustes",
                title = "Nueva contraseña.",
                description = "Mantén tu cuenta segura. Elige una contraseña que no uses en otros sitios."
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(28.dp), spotColor = Color(0x14000000)),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Neutral100),
                border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (uiState.errorMessage != null) {
                        ErrorMessage(
                            message = uiState.errorMessage,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (uiState.successMessage != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4CAF50))
                        ) {
                            Text(
                                text = uiState.successMessage,
                                modifier = Modifier.padding(12.dp),
                                color = Color(0xFF2E7D32),
                                style = AppTypography.bodySmall
                            )
                        }
                    }

                    SimpleInput(
                        label = "CONTRASEÑA ACTUAL",
                        value = uiState.currentPassword,
                        onValueChange = onCurrentPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        enabled = !uiState.isLoading
                    )

                    SimpleInput(
                        label = "NUEVA CONTRASEÑA",
                        value = uiState.newPassword,
                        onValueChange = onNewPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        enabled = !uiState.isLoading
                    )

                    SimpleInput(
                        label = "REPETIR CONTRASEÑA",
                        value = uiState.repeatPassword,
                        onValueChange = onRepeatPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        enabled = !uiState.isLoading
                    )

                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = Primary500
                        )
                    } else {
                        PrimaryButton(
                            text = "Actualizar Contraseña",
                            onClick = onSaveClick
                        )
                    }
                }
            }
        }
    }
}
