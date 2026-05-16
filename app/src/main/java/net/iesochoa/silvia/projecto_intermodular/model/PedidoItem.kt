package net.iesochoa.silvia.projecto_intermodular.model

/**
 * Representa un resumen de un pedido para ser mostrado en la lista de compras del usuario.
 *
 * @property id ID del pedido.
 * @property fecha Fecha de creación formateada.
 * @property estado Estado actual del pedido (PAGADO, PENDIENTE, etc.).
 * @property total Importe total formateado en moneda.
 */
data class PedidoItem(
    val id: Int = 0,
    val fecha: String,
    val estado: String,
    val total: String
)
