package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
@Composable
fun HomeScreen() {

    var search by remember { mutableStateOf("") }

    val promociones = listOf(
        CardItem(R.drawable.ic_launcher_foreground, "Pan", "0,75 €", "0,65 €"),
        CardItem(R.drawable.ic_launcher_foreground, "Pan", "0,75 €", "0,65 €"),
        CardItem(R.drawable.ic_launcher_foreground, "Pan", "0,75 €", "0,65 €"),
        CardItem(R.drawable.ic_launcher_foreground, "Pan", "0,75 €", "0,65 €")
    )

    val topVentas = listOf(
        CardItem(R.drawable.ic_launcher_foreground, "Pan", null, "0,65 €"),
        CardItem(R.drawable.ic_launcher_foreground, "Pan", null, "0,65 €")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🔹 HEADER
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bienvenido Usuario",
                    style = AppTypography.headlineSmall,
                    color = Secondary600
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Perfil",
                    tint = Primary600,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // 🔹 BUSCADOR
        item {
            SearchBar(
                query = search,
                onQueryChange = { search = it }
            )
        }

        // 🔹 ÚLTIMAS PROMOCIONES
        item {
            Text(text = "Últimas promociones")
        }

        item {
            CardList(items = promociones)
        }

        // 🔹 TOP VENTAS
        item {
            Text(text = "Top ventas")
        }

        item {
            CardList(items = topVentas)
        }
    }
}
