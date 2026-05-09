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
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSearchChange: (String) -> Unit,
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
                title = "La Croassantina",
                showSearch = true,
                searchQuery = uiState.searchQuery,
                onSearchChange = onSearchChange,
                onBackClick = onBackClick,
                onProfileClick = onProfileClick
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
                    SectionHeader(title = "Novedades del Obrador", eyebrow = "Recién salido")
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
                    SectionHeader(title = "Favoritos de la Casa", eyebrow = "Lo más vendido")
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
                        Text(text = "No hay productos disponibles actualmente.", style = AppTypography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 🔹 Left Panel
        Card(
            modifier = Modifier.weight(1.3f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Primary100),
            border = androidx.compose.foundation.BorderStroke(1.dp, Primary200)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "TRADICIÓN EN CADA BOCADO",
                    style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary500
                )
                Text(
                    text = "Pan y repostería artesanal.",
                    style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = TextPrimary
                )
                Text(
                    text = "Horneados cada mañana con ingredientes naturales.",
                    style = AppTypography.bodySmall,
                    color = TextPrimary.copy(alpha = 0.8f)
                )
            }
        }

        // 🔹 Right Panel
        val secondaryGradient = Brush.verticalGradient(
            colors = listOf(Secondary400, Secondary600)
        )
        Card(
            modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .background(secondaryGradient)
                    .fillMaxHeight()
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
