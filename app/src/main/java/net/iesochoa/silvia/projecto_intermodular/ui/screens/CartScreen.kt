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
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.SearchBar
import net.iesochoa.silvia.projecto_intermodular.ui.components.SelectorOfertas
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600
@Composable
fun CartScreen() {

    val products = listOf(
        HorizontalCardItem("Pan", "", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan integral", "0,80€", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Bollería", "", rightLabel = "Precio", rightValue = "Desde 1,20€", imageRes = R.drawable.croissant),
    )

    // 🔹 Ofertas que vendrían del backend
    val ofertas = listOf(
        Oferta("1", "Promo 10%", 0.10),
        Oferta("2", "Cliente frecuente", 0.15),
        Oferta("3", "Navidad", 0.20)
    )

    // 🔹 Estado levantado (luego irá al ViewModel)
    var ofertasSeleccionadas by remember { mutableStateOf(setOf<String>()) }

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

        // 🔹 PRODUCTOS
        item {
            HorizontalCardList(
                items = products,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 🔹 TÍTULO PROMOCIONES
        item {
            Text(
                text = "Aplicar promociones",
                style = AppTypography.titleMedium,
                color = Secondary600
            )
        }

        // 🔹 SELECTOR DE OFERTAS
        item {
            SelectorOfertas(
                ofertas = ofertas,
                onSeleccionCambio = { seleccionadas ->
                    ofertasSeleccionadas = seleccionadas
                }
            )
        }

        // 🔹 DEBUG / TOTAL / ENVÍO BACKEND
        item {
            Text(
                text = "Ofertas seleccionadas: ${ofertasSeleccionadas.joinToString()}",
                style = AppTypography.bodyMedium
            )
        }
    }
}
