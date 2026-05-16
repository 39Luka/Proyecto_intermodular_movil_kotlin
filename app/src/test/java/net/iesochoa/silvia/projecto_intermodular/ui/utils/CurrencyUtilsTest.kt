package net.iesochoa.silvia.projecto_intermodular.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyUtilsTest {

    /**
     * CP-22: toCurrency_formats_double_correctly
     * Verifica que los valores numéricos se formateen con el símbolo del Euro y dos posiciones decimales.
     */
    @Test
    fun `toCurrency formats double correctly`() {
        val price = 10.5
        assertEquals("€10.50", price.toCurrency())
    }

    /**
     * CP-22.1: toCurrency_handles_null_with_zero
     * Valida que si el precio es nulo, la utilidad devuelva un formato de "€0.00" de forma segura.
     */
    @Test
    fun `toCurrency handles null with zero`() {
        val price: Double? = null
        assertEquals("€0.00", price.toCurrency())
    }

    /**
     * CP-22.2: toCurrency_formats_large_values_correctly
     * Comprueba el redondeo y formateo correcto para valores con más de dos decimales.
     */
    @Test
    fun `toCurrency formats large values correctly`() {
        val price = 1234.567
        assertEquals("€1234.57", price.toCurrency())
    }
}
