package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.PaddingValues
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 👈 2 tarjetas por fila
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            CustomCard(
                imageRes = item.imageRes,
                title = item.title,
                bottomText1 = item.bottomText1,
                bottomText2 = item.bottomText2
            )
        }
    }
}
