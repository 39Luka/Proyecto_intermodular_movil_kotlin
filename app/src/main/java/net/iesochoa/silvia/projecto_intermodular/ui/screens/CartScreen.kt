package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iesochoa.silvia.projecto_intermodular.model.CartUiState
import net.iesochoa.silvia.projecto_intermodular.model.CartItemState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import java.util.Locale

@Composable
fun CartScreen(
    uiState: CartUiState,
    onQuantityChange: (Int, Int) -> Unit,
    onRemoveItem: (Int) -> Unit,
    onPromotionSelected: (Int, Int?) -> Unit,
    onCheckoutClick: () -> Unit,
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
                title = "Mi carrito",
                onBackClick = onBackClick,
                onProfileClick = onProfileClick
            )
        }

        item {
            PageIntro(
                eyebrow = "Checkout",
                title = "Prepara tu pedido.",
                description = "Ajusta cantidades y aplica promociones directamente en cada producto."
            )
        }

        if (uiState.items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tu carrito está vacío.",
                        style = AppTypography.bodyLarge,
                        color = TextPrimary.copy(alpha = 0.5f)
                    )
                }
            }
        } else {
            items(uiState.items) { cartItem ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    HorizontalCard(
                        item = HorizontalCardItem(
                            id = cartItem.product.id,
                            title = cartItem.product.getDisplayTitle(),
                            description = cartItem.product.description ?: "",
                            leftLabel = "Cantidad",
                            leftValue = cartItem.quantity.toString(),
                            rightLabel = "Subtotal",
                            rightValue = "€${String.format(Locale.US, "%.2f", (cartItem.product.price ?: 0.0) * cartItem.quantity)}"
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    if (cartItem.applicablePromotions.isNotEmpty()) {
                        ItemPromotionSelector(
                            itemState = cartItem,
                            onPromotionSelected = { promoId -> 
                                onPromotionSelected(cartItem.product.id, promoId)
                            }
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Neutral100),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
                ) {
                    Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        if (uiState.error != null) {
                            ErrorMessage(
                                message = uiState.error,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                "Total final",
                                style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                            Text(
                                "€${String.format(Locale.US, "%.2f", uiState.total)}",
                                style = AppTypography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = Secondary500
                            )
                        }
                        
                        PrimaryButton(
                            text = if (uiState.isProcessing) "Procesando..." else "Finalizar compra",
                            onClick = onCheckoutClick,
                            enabled = !uiState.isProcessing
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPromotionSelector(
    itemState: CartItemState,
    onPromotionSelected: (Int?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedPromo = itemState.applicablePromotions.find { it.id == itemState.selectedPromotionId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(if (selectedPromo != null) Success100 else Primary100.copy(alpha = 0.5f))
                .border(
                    1.dp, 
                    if (selectedPromo != null) Success600.copy(alpha = 0.3f) else Primary200, 
                    RoundedCornerShape(12.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = if (selectedPromo != null) Success600 else Primary600,
                    modifier = Modifier.size(20.dp)
                )
                
                Text(
                    text = if (selectedPromo != null) 
                        "Promo aplicada: ${selectedPromo.description} (-${selectedPromo.discountPercentage}%)"
                    else 
                        "Aplicar promoción disponible",
                    style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (selectedPromo != null) Success600 else Primary600
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.85f).background(Neutral100)
            ) {
                DropdownMenuItem(
                    text = { Text("Sin promoción", style = AppTypography.bodyMedium) },
                    onClick = {
                        onPromotionSelected(null)
                        expanded = false
                    }
                )
                
                itemState.applicablePromotions.forEach { promo ->
                    DropdownMenuItem(
                        text = { 
                            Column {
                                Text(promo.description ?: "Descuento", style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Text("Ahorra un ${promo.discountPercentage}%", style = AppTypography.labelSmall, color = Secondary500)
                            }
                        },
                        onClick = {
                            onPromotionSelected(promo.id)
                            expanded = false
                        },
                        trailingIcon = {
                            if (promo.id == itemState.selectedPromotionId) {
                                Icon(Icons.Default.ExpandMore, contentDescription = null, tint = Success600)
                            }
                        }
                    )
                }
            }
        }
    }
}
