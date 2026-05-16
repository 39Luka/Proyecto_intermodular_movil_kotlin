package net.iesochoa.silvia.projecto_intermodular.model

/**
 * Modelo de datos que representa una tarjeta de producto en la interfaz de usuario.
 * Se utiliza para abstraer la complejidad del modelo de dominio [net.iesochoa.silvia.projecto_intermodular.data.Product]
 * y proporcionar solo los datos necesarios para la visualización.
 *
 * @property id ID único del producto.
 * @property imageRes ID de recurso de imagen local (fallback).
 * @property imageUrl URL o Base64 de la imagen del producto.
 * @property title Nombre o título visible del producto.
 * @property bottomText1 Texto descriptivo secundario (ej. descripción corta).
 * @property bottomText2 Precio formateado u otra información relevante.
 * @property categoryName Nombre de la categoría a la que pertenece.
 * @property isOutOfStock Indica si el producto no tiene existencias.
 */
data class CardItem(
    val id: Int = 0,
    val imageRes: Int = 0,
    val imageUrl: String? = null,
    val title: String,
    val bottomText1: String? = null,
    val bottomText2: String,
    val categoryName: String = "Obrador diario",
    val isOutOfStock: Boolean = false
)
