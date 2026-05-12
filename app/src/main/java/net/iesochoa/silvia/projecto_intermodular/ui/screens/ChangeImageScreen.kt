package net.iesochoa.silvia.projecto_intermodular.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import net.iesochoa.silvia.projecto_intermodular.ui.utils.decodeBase64ToBitmap

@Composable
fun ChangeImageScreen(
    uiState: ProfileUiState,
    onImageSelected: (Uri?) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: (() -> Unit)?
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onImageSelected(uri) }
    )

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
                description = "Selecciona una foto de tu galería para personalizar tu perfil."
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (uiState.errorMessage != null) {
                    ErrorMessage(
                        message = uiState.errorMessage!!,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Preview de la imagen seleccionada o la actual
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Primary100),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                uiState.selectedImageUri != null -> {
                                    AsyncImage(
                                        model = uiState.selectedImageUri,
                                        contentDescription = "Vista previa",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        placeholder = painterResource(id = R.drawable.profile),
                                        error = painterResource(id = R.drawable.profile)
                                    )
                                }
                                !uiState.profileImage.isNullOrEmpty() -> {
                                    val bitmap = uiState.profileImage!!.decodeBase64ToBitmap()
                                    if (bitmap != null) {
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = "Vista previa",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.profile),
                                            contentDescription = "Vista previa",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                                else -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.profile),
                                        contentDescription = "Vista previa",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    color = Primary500,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !uiState.isLoading,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Seleccionar de la Galería")
                        }

                        PrimaryButton(
                            text = if (uiState.isLoading) "Guardando..." else "Guardar Cambios",
                            onClick = onSaveClick,
                            enabled = uiState.selectedImageUri != null && !uiState.isLoading
                        )
                    }
                }
            }
        }
    }
}
