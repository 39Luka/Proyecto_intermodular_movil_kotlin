package net.iesochoa.silvia.projecto_intermodular.ui.utils

import net.iesochoa.silvia.projecto_intermodular.data.Category
import net.iesochoa.silvia.projecto_intermodular.data.Product
import net.iesochoa.silvia.projecto_intermodular.data.Promotion
import net.iesochoa.silvia.projecto_intermodular.model.CardItem
import net.iesochoa.silvia.projecto_intermodular.model.HorizontalCardItem

/**
 * Utilidad para mapear entidades de datos de Producto a modelos de UI.
 */
object ProductMapper {

    /**
     * Mapea un [Product] a un [CardItem] para su visualización en listas.
     */
    fun toCardItem(product: Product, categories: List<Category>): CardItem {
        val catName = product.category?.name 
            ?: categories.find { it.id == product.categoryId }?.name 
            ?: "Obrador"
            
        return CardItem(
            id = product.id,
            imageUrl = product.getDisplayImage(),
            title = product.getDisplayTitle(),
            bottomText1 = product.description,
            bottomText2 = product.price.toCurrency(),
            categoryName = catName,
            isOutOfStock = (product.stock ?: 0) <= 0
        )
    }

    /**
     * Mapea un [Product] y su [Promotion] asociada a un [HorizontalCardItem].
     */
    fun toHorizontalCardItem(product: Product, promo: Promotion, categories: List<Category>): HorizontalCardItem {
        val catName = product.category?.name 
            ?: categories.find { it.id == product.categoryId }?.name 
            ?: "Obrador"

        return HorizontalCardItem(
            id = product.id,
            title = product.getDisplayTitle(),
            description = promo.description ?: "",
            rightLabel = "Descuento",
            rightValue = "-${promo.discountPercentage}%",
            imageUrl = product.getDisplayImage(),
            categoryName = catName,
            isUsed = promo.used
        )
    }
}
