package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardList(
    items: List<CardItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { item ->
                    CustomCard(
                        imageRes = item.imageRes,
                        title = item.title,
                        bottomText1 = item.bottomText1,
                        bottomText2 = item.bottomText2,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Rellena si hay solo 1 item
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
