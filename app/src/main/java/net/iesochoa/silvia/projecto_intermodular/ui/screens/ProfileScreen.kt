package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onChangeImageClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onBackClick: (() -> Unit)?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            ScreenHeader(title = uiState.username, onBackClick = onBackClick)
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(120.dp)
                )

                PrimaryButton(text = "Cambiar imagen", onClick = onChangeImageClick, modifier = Modifier.fillMaxWidth())
                PrimaryButton(text = "Cambiar contraseña", onClick = onChangePasswordClick, modifier = Modifier.fillMaxWidth())
                PrimaryButton(text = "Cerrar sesión", onClick = onLogoutClick, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
