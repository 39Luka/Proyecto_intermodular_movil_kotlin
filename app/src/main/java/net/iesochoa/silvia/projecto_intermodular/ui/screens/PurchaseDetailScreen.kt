// ui/screens/PurchaseDetailScreen.kt
package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseRepository
import net.iesochoa.silvia.projecto_intermodular.ui.components.PageIntro
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import java.util.Locale
import javax.inject.Inject

@Composable
fun PurchaseDetailScreen(
    purchaseId: Int,
    viewModel: PurchaseDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(purchaseId) {
        viewModel.loadPurchase(purchaseId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ScreenHeader(
            title = "Pedido #${purchaseId}",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary500)
                }
            }

            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = Error600)
                }
            }

            uiState.purchase != null -> {
                PurchaseDetailContent(purchase = uiState.purchase!!)
            }
        }
    }
}

@Composable
private fun PurchaseDetailContent(
    purchase: Purchase
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PageIntro(
            eyebrow = "Seguimiento",
            title = "Detalle del pedido.",
            description = "Información detallada sobre los artículos, importes y estado de tu compra."
        )

        // 🔹 Info Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(26.dp), spotColor = Color(0x14000000)),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Neutral100),
            border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
        ) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "INFORMACIÓN GENERAL",
                    style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary500
                )

                InfoRow("Fecha", purchase.createdAt?.take(16)?.replace("T", " ") ?: "N/A")
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Estado", style = AppTypography.bodyMedium, color = TextPrimary.copy(alpha = 0.6f))
                    StatusBadge(status = purchase.status ?: "PENDIENTE")
                }

                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Neutral200.copy(alpha = 0.5f)))

                PriceRow("Subtotal", purchase.subtotal ?: 0.0)
                if ((purchase.discount ?: 0.0) > 0) {
                    PriceRow("Descuento", -(purchase.discount ?: 0.0), color = Success600)
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "Total final",
                        style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextPrimary
                    )
                    Text(
                        "€${String.format(Locale.US, "%.2f", purchase.total ?: 0.0)}",
                        style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = Secondary500
                    )
                }
            }
        }

        // 🔹 Items Section
        Text(
            text = "ARTÍCULOS INCLUIDOS",
            style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Primary500,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            purchase.items.forEach { item ->
                ItemCard(item)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = AppTypography.bodyMedium, color = TextPrimary.copy(alpha = 0.6f))
        Text(text = value, style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
    }
}

@Composable
private fun PriceRow(label: String, amount: Double, color: Color = TextPrimary) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = AppTypography.bodyMedium, color = TextPrimary.copy(alpha = 0.6f))
        Text(
            text = "€${String.format(Locale.US, "%.2f", amount)}",
            style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = color
        )
    }
}

@Composable
private fun ItemCard(item: net.iesochoa.silvia.projecto_intermodular.data.PurchaseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(20.dp), spotColor = Color(0x14000000)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName ?: "Producto",
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
                Text(
                    text = "Cantidad: ${item.quantity}",
                    style = AppTypography.labelLarge,
                    color = TextPrimary.copy(alpha = 0.5f)
                )
            }
            Text(
                text = "€${String.format(Locale.US, "%.2f", item.subtotal)}",
                style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = Secondary500
            )
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (bgColor, textColor) = when (status.uppercase()) {
        "PAID", "PAGADA", "DELIVERED" -> Success100 to Success600
        "CANCELLED", "CANCELADA" -> Error100 to Error600
        else -> Warning100 to Warning600
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(999.dp)
    ) {
        Text(
            text = status.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
            color = textColor
        )
    }
}

@HiltViewModel
class PurchaseDetailViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseDetailUiState())
    val uiState: StateFlow<PurchaseDetailUiState> = _uiState.asStateFlow()

    fun loadPurchase(purchaseId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val purchase = purchaseRepository.getPurchaseById(purchaseId)
                _uiState.value = _uiState.value.copy(
                    purchase = purchase,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar compra"
                )
            }
        }
    }
}

data class PurchaseDetailUiState(
    val purchase: Purchase? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
