package net.iesochoa.silvia.projecto_intermodular.data

import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.Oferta
import net.iesochoa.silvia.projecto_intermodular.ui.components.PedidoItem

object FakeRepository {

    // 🔹 Últimas promociones (HomeScreen)
    val promociones = listOf(
        CardItem(R.drawable.croissant, "Pan", "", "0,65€"),
        CardItem(R.drawable.croissant, "Pan integral", "0,80€", "0,65€"),
        CardItem(R.drawable.croissant, "Bollería", "", "Desde 1,20€"),
        CardItem(R.drawable.croissant, "Croissant", "1,20€", "0,95€"),
        CardItem(R.drawable.croissant, "Pan dulce", "", "0,75€"),
        CardItem(R.drawable.croissant, "Panecillo", "0,90€", "0,70€")
    )

    // 🔹 Top Ventas (HomeScreen)
    val topVentas = listOf(
        CardItem(R.drawable.croissant, "Croissant clásico", "1,10€", "0,95€"),
        CardItem(R.drawable.croissant, "Baguette", "0,90€", "0,75€"),
        CardItem(R.drawable.croissant, "Pan integral", "0,85€", "0,65€"),
        CardItem(R.drawable.croissant, "Pan de chocolate", "1,50€", "1,20€"),
        CardItem(R.drawable.croissant, "Panecillo de semillas", "0,95€", "0,80€"),
        CardItem(R.drawable.croissant, "Bollería surtida", "1,30€", "1,10€")
    )

    // 🔹 Productos generales (CatalogScreen)
    val cardProducts = promociones + topVentas

    // 🔹 Productos para CartScreen y OffersScreen
    val horizontalProducts = listOf(
        HorizontalCardItem("Pan", "", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan integral", "0,80€", rightLabel = "Precio", rightValue = "0,65€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Bollería", "", rightLabel = "Precio", rightValue = "Desde 1,20€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Croissant", "1,20€", rightLabel = "Precio", rightValue = "0,95€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan dulce", "", rightLabel = "Precio", rightValue = "0,75€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Baguette", "0,90€", rightLabel = "Precio", rightValue = "0,75€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Pan de chocolate", "1,50€", rightLabel = "Precio", rightValue = "1,20€", imageRes = R.drawable.croissant),
        HorizontalCardItem("Bollería surtida", "1,30€", rightLabel = "Precio", rightValue = "1,10€", imageRes = R.drawable.croissant)
    )

    // 🔹 Ofertas para CartScreen
    val ofertas = listOf(
        Oferta("1", "Promo 10%", 0.10),
        Oferta("2", "Cliente frecuente", 0.15),
        Oferta("3", "Navidad", 0.20)
    )

    // 🔹 Pedidos para PurchasesScreen
    val pedidos = listOf(
        PedidoItem("01/01/2026", "Entregado", "1,50€"),
        PedidoItem("02/01/2026", "Pendiente", "2,75€"),
        PedidoItem("03/01/2026", "Cancelado", "0,95€"),
        PedidoItem("04/01/2026", "Entregado", "3,20€"),
        PedidoItem("05/01/2026", "Pendiente", "1,75€")
    )
}
