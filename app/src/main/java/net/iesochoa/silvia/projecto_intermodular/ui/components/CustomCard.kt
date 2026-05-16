package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.model.CardItem
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Tarjeta vertical para mostrar productos en cuadrículas o listas.
 * Incluye imagen, título, descripción corta, precio y un indicador de "Agotado".
 */
/**
 * Tarjeta vertical para mostrar productos en cuadrículas o listas.
 * Incluye imagen, título, descripción corta, precio y un indicador de "Agotado".
 *
 * @param item Objeto con los datos del producto para la UI.
 * @param modifier Modificador de Compose para el tamaño y disposición.
 * @param onClick Acción al pulsar sobre la tarjeta (solo si hay stock).
 */
@Composable
fun CustomCard(
    item: CardItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val alpha = if (item.isOutOfStock) 0.6f else 1f
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !item.isOutOfStock) { onClick() }
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0x14000000)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isOutOfStock) Neutral200 else Neutral100
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (item.isOutOfStock) Neutral300 else Neutral200)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.9f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (item.isOutOfStock) Neutral300 else Primary100)
                ) {
                    AppAsyncImage(
                        model = item.imageUrl ?: if (item.imageRes != 0) item.imageRes else null,
                        contentDescription = item.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = alpha
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = item.categoryName.uppercase(),
                    style = AppTypography.labelMedium,
                    color = if (item.isOutOfStock) TextPrimary.copy(alpha = 0.4f) else Primary500
                )

                Text(
                    text = item.title,
                    style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.Normal),
                    color = if (item.isOutOfStock) TextPrimary.copy(alpha = 0.5f) else TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!item.bottomText1.isNullOrEmpty()) {
                    Text(
                        text = item.bottomText1,
                        style = AppTypography.bodySmall,
                        color = TextPrimary.copy(alpha = if (item.isOutOfStock) 0.3f else 0.6f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Primary200.copy(alpha = if (item.isOutOfStock) 0.2f else 0.5f))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = item.bottomText2,
                        style = AppTypography.titleLarge,
                        color = if (item.isOutOfStock) Secondary500.copy(alpha = 0.5f) else Secondary500
                    )
                    
                    Text(
                        text = if (item.isOutOfStock) "No disponible" else "Ver detalle",
                        style = AppTypography.labelLarge,
                        color = if (item.isOutOfStock) TextPrimary.copy(alpha = 0.4f) else Primary500
                    )
                }
            }

            if (item.isOutOfStock) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(300.dp) // Ancho suficiente para la diagonal
                            .height(32.dp)
                            .align(androidx.compose.ui.Alignment.Center)
                            .graphicsLayer {
                                rotationZ = -35f
                            }
                            .background(Secondary500.copy(alpha = 0.9f)),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(
                            text = "AGOTADO",
                            color = Color.White,
                            style = AppTypography.labelLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Rejilla de tarjetas de productos dispuestas de dos en dos.
 * @param items Lista de productos a mostrar.
 * @param onItemClick Acción al pulsar sobre un producto.
 */
@Composable
fun CardList(
    items: List<CardItem>,
    onItemClick: (CardItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val chunkedItems = items.chunked(2)
        chunkedItems.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    CustomCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
