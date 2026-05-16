package net.iesochoa.silvia.projecto_intermodular.ui.utils

import java.util.Locale

/**
 * Utilidades para formatear valores monetarios en la aplicación.
 */
object CurrencyUtils {
    /**
     * Formatea un valor Double a formato de moneda Euro (e.g., 10.5 -> €10.50).
     */
    fun formatPrice(price: Double?): String {
        return "€${String.format(Locale.US, "%.2f", price ?: 0.0)}"
    }
}

/**
 * Función de extensión para formatear Double? a moneda directamente.
 */
fun Double?.toCurrency(): String = CurrencyUtils.formatPrice(this)
