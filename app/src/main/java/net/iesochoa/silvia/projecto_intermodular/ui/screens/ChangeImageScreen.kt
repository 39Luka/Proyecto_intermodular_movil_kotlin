package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun ChangeImageScreen(
    uiState: ProfileUiState,
    onImagePathChange: (String) -> Unit,
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
                title = "Editar Imagen",
                onBackClick = onBackClick
            )
        }

        item {
            PageIntro(
                eyebrow = "Ajustes",
                title = "Imagen de perfil.",
                description = "Introduce la URL de tu nueva foto para personalizar tu experiencia."
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
                    SimpleInput(
                        label = "URL DE LA IMAGEN",
                        value = uiState.newImagePath,
                        onValueChange = onImagePathChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    PrimaryButton(
                        text = "Actualizar Imagen",
                        onClick = onSaveClick
                    )
                }
            }
        }
    }
}
