package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Tarjeta horizontal diseñada para destacar promociones o items detallados.
 * Muestra información de descuento y permite un estado de "Usada" con opacidad reducida.
 */
/**
 * Tarjeta horizontal diseñada para destacar promociones o items detallados.
 * Muestra información de descuento y permite un estado de "Usada" con opacidad reducida.
 *
 * @param item Objeto con los datos a mostrar en la tarjeta.
 * @param onClick Acción al pulsar sobre la tarjeta (si no está usada).
 * @param modifier Modificador de Compose.
 */
@Composable
fun HorizontalCard(
    item: HorizontalCardItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val alpha = if (item.isUsed) 0.5f else 1f
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !item.isUsed) { onClick() }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(26.dp),
                spotColor = Color(0x14000000)
            ),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isUsed) Neutral200 else Neutral100
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isUsed) Neutral300 else Neutral200)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.categoryName.uppercase(),
                        style = AppTypography.labelMedium,
                        color = if (item.isUsed) TextPrimary.copy(alpha = 0.4f) else Primary500
                    )
                    
                    if (item.isUsed) {
                        androidx.compose.material3.Surface(
                            color = Neutral400,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "YA USADA",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    }
                }

                Text(
                    text = item.title,
                    style = AppTypography.titleMedium,
                    color = if (item.isUsed) TextPrimary.copy(alpha = 0.5f) else TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.description.isNotEmpty()) {
                    Text(
                        text = item.description,
                        style = AppTypography.bodySmall,
                        color = TextPrimary.copy(alpha = if (item.isUsed) 0.3f else 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Neutral200.copy(alpha = if (item.isUsed) 0.2f else 0.5f))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.leftLabel != null && item.leftValue != null) {
                        Column {
                            Text(
                                text = item.leftLabel,
                                style = AppTypography.labelMedium,
                                color = TextPrimary.copy(alpha = if (item.isUsed) 0.3f else 0.6f)
                            )
                            Text(
                                text = item.leftValue,
                                style = AppTypography.bodyMedium,
                                color = if (item.isUsed) TextPrimary.copy(alpha = 0.4f) else TextPrimary
                            )
                        }
                    }

                    if (item.rightLabel != null && item.rightValue != null) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = item.rightLabel,
                                style = AppTypography.labelMedium,
                                color = Secondary500.copy(alpha = if (item.isUsed) 0.3f else 0.6f)
                            )
                            Text(
                                text = item.rightValue,
                                style = AppTypography.bodyLarge,
                                color = if (item.isUsed) Secondary500.copy(alpha = 0.4f) else Secondary500
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (item.isUsed) Neutral300 else Secondary100)
            ) {
                AppAsyncImage(
                    model = item.imageUrl ?: if (item.imageRes != null && item.imageRes != 0) item.imageRes else null,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = alpha
                )
            }
        }
    }
}

/**
 * Lista vertical de tarjetas horizontales.
 * @param items Lista de elementos a visualizar.
 * @param onItemClick Acción al pulsar en cualquier tarjeta.
 */
@Composable
fun HorizontalCardList(
    items: List<HorizontalCardItem>,
    onItemClick: (HorizontalCardItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.forEach { item ->
            HorizontalCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}
