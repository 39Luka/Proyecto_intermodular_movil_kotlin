package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Pantalla de inicio de la aplicación.
 * Muestra el Hero (Sección destacada), novedades y productos más vendidos.
 */
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetryClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp)
    ) {

        item {
            ScreenHeader(
                title = stringResource(R.string.home_title),
                showSearch = false,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick,
                profileImage = uiState.userProfileImage
            )
        }

        if (uiState.error != null) {
            item {
                ErrorMessage(message = uiState.error, onRetry = onRetryClick)
            }
        }

        item {
            HeroSection()
        }

        if (uiState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary500)
                }
            }
        } else {
            // 🔹 Sección Novedades
            if (uiState.filteredPromociones.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = stringResource(R.string.home_section_latest),
                        eyebrow = stringResource(R.string.home_eyebrow_latest)
                    )
                }

                item {
                    CardList(
                        items = uiState.filteredPromociones,
                        onItemClick = { onProductClick(it.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // 🔹 Sección Favoritos
            if (uiState.filteredTopVentas.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = stringResource(R.string.home_section_favorites),
                        eyebrow = stringResource(R.string.home_eyebrow_favorites)
                    )
                }

                item {
                    CardList(
                        items = uiState.filteredTopVentas,
                        onItemClick = { onProductClick(it.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            if (uiState.filteredPromociones.isEmpty() && uiState.filteredTopVentas.isEmpty() && uiState.error == null) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.home_no_products),
                            style = AppTypography.bodyMedium,
                            color = TextPrimary.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroSection() {
    val lilacGradient = Brush.linearGradient(
        colors = listOf(Neutral100.copy(alpha = 0.97f), Primary100.copy(alpha = 0.64f))
    )
    val orangeGradient = Brush.verticalGradient(
        colors = listOf(Secondary400, Secondary600)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max), // Igualar altura de ambas tarjetas
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 🔹 Left Panel - Estilo PageIntro
        Card(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            border = androidx.compose.foundation.BorderStroke(1.dp, Primary200.copy(alpha = 0.55f))
        ) {
            Column(
                modifier = Modifier
                    .background(lilacGradient)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center // Centrado vertical para equilibrar
            ) {
                Text(
                    text = stringResource(R.string.home_hero_label),
                    style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary500
                )
                Text(
                    text = stringResource(R.string.home_hero_title),
                    style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = TextPrimary
                )
                Text(
                    text = stringResource(R.string.home_hero_description),
                    style = AppTypography.bodySmall,
                    color = TextPrimary.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // 🔹 Right Panel - Naranja
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .background(orangeGradient)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MetricItem("100%", "Natural")
                MetricItem("24h", "Reposo")
            }
        }
    }
}

@Composable
private fun MetricItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = Color.White
        )
        Text(
            text = label,
            style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White.copy(alpha = 0.9f)
        )
    }
}

@Composable
private fun SectionHeader(title: String, eyebrow: String) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = eyebrow.uppercase(),
            style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Primary500
        )
        Text(
            text = title,
            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = Secondary600
        )
    }
}
