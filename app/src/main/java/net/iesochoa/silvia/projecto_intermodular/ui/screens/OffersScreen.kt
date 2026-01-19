package net.iesochoa.silvia.projecto_intermodular.ui.screens

import HorizontalCardList
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.SearchBar

@Composable
fun OffersScreen() {

    var searchQuery by remember { mutableStateOf("") }

    val products = listOf(
        HorizontalCardItem("Pan", "", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan integral", "0,80€", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Bollería", "", rightLabel = "Precio", rightValue = "Desde 1,20€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Croissant", "1,20€", rightLabel = "Precio", rightValue = "0,95€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan", "", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan integral", "0,80€", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Bollería", "", rightLabel = "Precio", rightValue = "Desde 1,20€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Croissant", "1,20€", rightLabel = "Precio", rightValue = "0,95€", imageRes = R.drawable.croissant)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // 🔹 HEADER
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 🔹 SEARCH
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 🔹 HORIZONTAL CARD LIST
        item {
            HorizontalCardList(
                items = products, // todos los productos juntos
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
