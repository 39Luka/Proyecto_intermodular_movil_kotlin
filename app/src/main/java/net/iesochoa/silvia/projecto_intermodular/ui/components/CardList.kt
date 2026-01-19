package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.unit.dp


@Composable
fun CardList(
    items: List<CardItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val chunkedItems = items.chunked(2)
        chunkedItems.forEach { rowItems ->
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
                        modifier = Modifier.weight(1f) // 👈 igual tamaño
                    )
                }

                // Si solo hay 1 tarjeta, rellenamos el hueco con un Spacer
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
