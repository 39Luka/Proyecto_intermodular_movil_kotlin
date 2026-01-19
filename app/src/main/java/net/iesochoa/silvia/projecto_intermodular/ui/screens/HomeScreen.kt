package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.SearchBar
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600
@Composable
fun HomeScreen() {

    var searchQuery by remember { mutableStateOf("") }

    val products = listOf(
        CardItem(R.drawable.croissant, "Pan", "", "0,65€"),
        CardItem(R.drawable.croissant, "Pan integral", "0,80€", "0,65€"),
        CardItem(R.drawable.croissant, "Bollería", "", "Desde 1,20€"),
        CardItem(R.drawable.croissant, "Croissant", "1,20€", "0,95€")
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
                Text(
                    text = "Bienvenido Usuario",
                    style = AppTypography.headlineSmall,
                    color = Secondary600
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
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it } // solo UI
            )
        }

        // 🔹 TÍTULO SECCIÓN
        item {
            Text(
                text = "Últimas promociones",
                style = AppTypography.bodyMedium,
                color = Secondary600
            )
        }

        // 🔹 GRID PRODUCTOS
        item {
            CardList(
                items = products, // 👈 todos los productos juntos
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Top Ventas",
                style = AppTypography.bodyMedium,
                color = Secondary600
            )
        }

        // 🔹 GRID PRODUCTOS
        item {
            CardList(
                items = products, // 👈 todos los productos juntos
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}


