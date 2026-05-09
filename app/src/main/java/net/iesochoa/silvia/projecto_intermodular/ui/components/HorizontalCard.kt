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
import coil.compose.AsyncImage
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

data class HorizontalCardItem(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val leftLabel: String? = null,
    val leftValue: String? = null,
    val rightLabel: String? = null,
    val rightValue: String? = null,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val categoryName: String = "Obrador"
)

@Composable
fun HorizontalCard(
    item: HorizontalCardItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(26.dp),
                spotColor = Color(0x14000000)
            ),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = androidx.compose.foundation.BorderStroke(1.dp, Neutral200)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.categoryName.uppercase(),
                    style = AppTypography.labelMedium,
                    color = Primary500
                )

                Text(
                    text = item.title,
                    style = AppTypography.titleMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.description.isNotEmpty()) {
                    Text(
                        text = item.description,
                        style = AppTypography.bodySmall,
                        color = TextPrimary.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Neutral200.copy(alpha = 0.5f))
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
                                color = TextPrimary.copy(alpha = 0.6f)
                            )
                            Text(
                                text = item.leftValue,
                                style = AppTypography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }

                    if (item.rightLabel != null && item.rightValue != null) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = item.rightLabel,
                                style = AppTypography.labelMedium,
                                color = Secondary500.copy(alpha = 0.6f)
                            )
                            Text(
                                text = item.rightValue,
                                style = AppTypography.bodyLarge,
                                color = Secondary500
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Secondary100)
            ) {
                AsyncImage(
                    model = item.imageUrl ?: if (item.imageRes != null && item.imageRes != 0) item.imageRes else R.drawable.croissant,
                    contentDescription = item.title,
                    placeholder = painterResource(id = R.drawable.croissant),
                    error = painterResource(id = R.drawable.croissant),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

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
