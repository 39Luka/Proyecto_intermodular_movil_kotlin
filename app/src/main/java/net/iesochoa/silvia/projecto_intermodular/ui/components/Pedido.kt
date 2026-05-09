package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

data class PedidoItem(
    val id: Int = 0,
    val fecha: String,
    val estado: String,
    val total: String
)

@Composable
fun PedidoCard(
    pedido: PedidoItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(4.dp, RoundedCornerShape(22.dp), spotColor = Color(0x14000000)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "PEDIDO #${pedido.id}",
                    style = AppTypography.labelMedium,
                    color = Primary500
                )
                
                Text(
                    text = pedido.fecha,
                    style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                StatusBadge(status = pedido.estado)
            }

            Text(
                text = pedido.total,
                style = AppTypography.headlineSmall,
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
            style = AppTypography.labelLarge.copy(fontSize = 10.sp),
            color = textColor
        )
    }
}

@Composable
fun PedidoList(
    pedidos: List<PedidoItem>,
    onItemClick: (PedidoItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        pedidos.forEach { pedido ->
            PedidoCard(
                pedido = pedido,
                onClick = { onItemClick(pedido) }
            )
        }
    }
}
