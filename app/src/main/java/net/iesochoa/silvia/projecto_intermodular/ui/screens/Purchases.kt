package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoCard
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoList
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.components.SearchBar

@Composable
fun PurchasesScreen(){

    var searchQuery by remember { mutableStateOf("") }

    // Lista de pedidos de ejemplo
    val pedidos = listOf(
        PedidoItem("01/01/2026", "Entregado", "1,50€"),
        PedidoItem("02/01/2026", "Pendiente", "2,75€"),
        PedidoItem("03/01/2026", "Cancelado", "0,95€"),
        PedidoItem("04/01/2026", "Entregado", "3,20€"),
        PedidoItem("05/01/2026", "Pendiente", "1,75€")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            ScreenHeader(
                showBack = true,
                showSearch = true,
                showFilter = true,
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it }
            )
        }


        // 🔹 LISTA DE PEDIDOS
        item { // ✅ SOLO UN ITEM, que es la lista completa
            PedidoList(pedidos = pedidos)
        }
    }
}
