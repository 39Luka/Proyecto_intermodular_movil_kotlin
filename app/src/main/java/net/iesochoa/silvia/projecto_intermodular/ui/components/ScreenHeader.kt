package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import net.iesochoa.silvia.projecto_intermodular.ui.utils.decodeBase64ToBitmap

/**
 * Cabecera accesible con áreas de toque de 48dp.
 */
/**
 * Cabecera estándar para las pantallas de la aplicación.
 * Soporta título central, botón de retroceso, imagen de perfil del usuario,
 * barra de búsqueda integrada y botón de filtros.
 */
@Composable
fun ScreenHeader(
    title: String? = null,
    onBackClick: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,
    profileImage: String? = null,
    showSearch: Boolean = false,
    showFilter: Boolean = false,
    searchQuery: String = "",
    searchPlaceholder: String = "Buscar",
    onSearchChange: (String) -> Unit = {},
    onSearchClick: (() -> Unit)? = null,
    onFilterClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Área de toque mínima de 48dp (estándar de accesibilidad)
                if (onBackClick != null) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .semantics { role = Role.Button }
                            .clickable(
                                onClickLabel = "Regresar a la pantalla anterior",
                                onClick = onBackClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Botón regresar",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                if (title != null) {
                    Text(
                        text = title,
                        style = AppTypography.titleLarge,
                        color = Secondary600,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                if (onProfileClick != null) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .semantics { role = Role.Button }
                            .clickable(
                                onClickLabel = "Ver mi perfil",
                                onClick = onProfileClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier.size(34.dp).clip(CircleShape)) {
                            if (!profileImage.isNullOrEmpty()) {
                                AppAsyncImage(
                                    model = profileImage,
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                DefaultProfileIcon()
                            }
                        }
                    }
                } else if (onBackClick != null) {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }

            if (showSearch) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = onSearchChange,
                        placeholderText = searchPlaceholder,
                        modifier = Modifier.weight(1f),
                        cornerRadius = 999.dp,
                        textColor = Primary600,
                        readOnly = onSearchClick != null,
                        onClick = onSearchClick
                    )

                    if (showFilter) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .semantics { role = Role.Button }
                                .clickable(
                                    onClickLabel = "Abrir opciones de filtrado",
                                    onClick = onFilterClick
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Filtros",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultProfileIcon() {
    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = "Ir al perfil",
        modifier = Modifier.fillMaxSize()
    )
}
