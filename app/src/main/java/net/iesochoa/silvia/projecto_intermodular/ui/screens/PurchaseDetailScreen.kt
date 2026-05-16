// ui/screens/PurchaseDetailScreen.kt
package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import net.iesochoa.silvia.projecto_intermodular.data.Purchase
import net.iesochoa.silvia.projecto_intermodular.data.PurchaseItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.PageIntro
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import net.iesochoa.silvia.projecto_intermodular.viewmodel.PurchaseDetailViewModel
import java.util.Locale

/**
 * Pantalla de detalle de pedido optimizada: Sin FABs redundantes y scroll corregido.
 */
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
            title = "Detalle del Pedido",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary500)
                }
            }

            uiState.error != null -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: ${uiState.error}", color = Error600)
                        Button(onClick = { viewModel.loadPurchase(purchaseId) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            uiState.purchase != null -> {
                PurchaseDetailContent(
                    purchase = uiState.purchase!!,
                    isProcessing = uiState.isProcessing,
                    onPayClick = { viewModel.payPurchase() },
                    onCancelClick = { viewModel.cancelPurchase() },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PurchaseDetailContent(
    purchase: Purchase,
    isProcessing: Boolean,
    onPayClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PageIntro(
            eyebrow = "Pedido #${purchase.id}",
            title = "Resumen de compra",
            description = "Consulta aquí los artículos y el estado actual de tu transacción."
        )

        // Tarjeta de información con acciones integradas (No flotantes)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(26.dp), spotColor = Color(0x14000000))
                .semantics(mergeDescendants = true) { },
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

                PriceRow("Total pagado", purchase.total ?: 0.0)

                // Botones de acción dentro de la tarjeta para mejor contexto visual
                val canModify = purchase.status?.uppercase() != "PAID" && 
                                purchase.status?.uppercase() != "PAGADA" && 
                                purchase.status?.uppercase() != "CANCELLED" && 
                                purchase.status?.uppercase() != "CANCELADA"

                if (canModify) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onPayClick,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary500),
                        enabled = !isProcessing
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text("Pagar ahora", style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    OutlinedButton(
                        onClick = onCancelClick,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Error600),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Error600),
                        enabled = !isProcessing
                    ) {
                        Text("Cancelar pedido", style = AppTypography.labelLarge)
                    }
                }
            }
        }

        Text(
            text = "ARTÍCULOS",
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
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = AppTypography.bodyMedium, color = TextPrimary.copy(alpha = 0.6f))
        Text(text = value, style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = TextPrimary)
    }
}

@Composable
private fun PriceRow(label: String, amount: Double) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = AppTypography.bodyMedium, color = TextPrimary.copy(alpha = 0.6f))
        Text(
            text = "€${String.format(Locale.US, "%.2f", amount)}",
            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Secondary500
        )
    }
}

@Composable
private fun ItemCard(item: PurchaseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200.copy(alpha = 0.5f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.productName ?: "Producto", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text(text = "Cantidad: ${item.quantity}", style = AppTypography.labelLarge, color = TextPrimary.copy(alpha = 0.5f))
            }
            Text(
                text = "€${String.format(Locale.US, "%.2f", (item.unitPrice ?: 0.0) * (item.quantity ?: 0))}",
                style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
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
    Surface(color = bgColor, shape = RoundedCornerShape(999.dp)) {
        Text(
            text = status.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
    }
}
