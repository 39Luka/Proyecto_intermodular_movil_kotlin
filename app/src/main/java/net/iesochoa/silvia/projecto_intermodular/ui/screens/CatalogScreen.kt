package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.SearchBar
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.Screen
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600
@Composable
fun CatalogScreen() {

    var searchQuery by remember { mutableStateOf("") }

    val products = listOf(
        CardItem(R.drawable.croissant, "Pan", "", "0,65€"),
        CardItem(R.drawable.croissant, "Pan integral", "0,80€", "0,65€"),
        CardItem(R.drawable.croissant, "Bollería", "", "Desde 1,20€"),
        CardItem(R.drawable.croissant, "Croissant", "1,20€", "0,95€"),
        CardItem(R.drawable.croissant, "Pan", "", "0,65€"),
        CardItem(R.drawable.croissant, "Pan integral", "0,80€", "0,65€"),
        CardItem(R.drawable.croissant, "Bollería", "", "Desde 1,20€"),
        CardItem(R.drawable.croissant, "Croissant", "1,20€", "0,95€"),
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
                    modifier = Modifier.weight(1f) // 👈 ocupa todo el espacio disponible
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 🔹 GRID PRODUCTOS
        item {
            CardList(
                items = products, // todos los productos juntos
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
