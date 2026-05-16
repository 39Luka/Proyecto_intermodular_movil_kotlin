package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.AppAsyncImage
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Pantalla de perfil del usuario.
 * Muestra información básica y enlaces para editar imagen, contraseña y cerrar sesión.
 */
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onChangeImageClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogoutClick: () -> Unit,
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
                title = "Mi Perfil",
                onBackClick = onBackClick,
                profileImage = uiState.profileImage
            )
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 🔹 Avatar
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .shadow(12.dp, CircleShape, spotColor = Primary500)
                        .background(Primary100, CircleShape)
                        .padding(4.dp)
                ) {
                    AppAsyncImage(
                        model = uiState.profileImage ?: R.drawable.profile,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = uiState.username,
                    style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = TextPrimary
                )

                Text(
                    text = "Gestiona tu cuenta y seguridad.",
                    style = AppTypography.bodyMedium,
                    color = TextPrimary.copy(alpha = 0.6f)
                )
            }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "AJUSTES DE CUENTA",
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Primary500
                    )

                    PrimaryButton(
                        text = "Cambiar imagen de perfil",
                        onClick = onChangeImageClick
                    )

                    PrimaryButton(
                        text = "Actualizar contraseña",
                        onClick = onChangePasswordClick
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Neutral200.copy(alpha = 0.5f)))
                    
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onLogoutClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Error600
                        ),
                        shape = RoundedCornerShape(999.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Error200)
                    ) {
                        Text(
                            text = "Cerrar sesión",
                            modifier = Modifier.padding(vertical = 8.dp),
                            style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
