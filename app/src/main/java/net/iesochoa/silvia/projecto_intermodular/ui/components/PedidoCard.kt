package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BackgroundColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BorderColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500


@Composable
fun PedidoCard(
    pedido: PedidoItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(BackgroundColor, RoundedCornerShape(12.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = pedido.fecha,
            style = AppTypography.bodyMedium,
            color = Secondary500,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = pedido.estado,
            style = AppTypography.bodyMedium,
            color = Secondary500,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = pedido.total,
            style = AppTypography.bodyMedium,
            color = Secondary500,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PedidoList(
    pedidos: List<PedidoItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        pedidos.forEach { pedido ->
            PedidoCard(pedido = pedido)
        }
    }
}
