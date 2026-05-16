package net.iesochoa.silvia.projecto_intermodular.model

/**
 * Modelo que representa una oferta o promoción simplificada para la UI.
 */
data class Oferta(
    val id: String,
    val nombre: String,
    val descuento: Double // o porcentaje
)
