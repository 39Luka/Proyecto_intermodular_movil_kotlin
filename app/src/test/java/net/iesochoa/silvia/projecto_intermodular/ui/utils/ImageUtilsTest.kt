package net.iesochoa.silvia.projecto_intermodular.ui.utils

import android.util.Base64
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ImageUtilsTest {

    @Before
    fun setup() {
        mockkStatic(Base64::class)
    }

    /**
     * CP-37: decodeBase64ToBitmap_returns_null_for_invalid_string
     * Verifica que la utilidad de imagen maneje correctamente cadenas corruptas o no válidas devolviendo null.
     */
    @Test
    fun decodeBase64ToBitmap_returns_null_for_invalid_string() {
        val invalidBase64 = "not-a-base64-string"
        
        // Mock Base64.decode to throw exception for invalid input
        every { Base64.decode(any<String>(), Base64.DEFAULT) } throws IllegalArgumentException()
        
        val result = invalidBase64.decodeBase64ToBitmap()
        assertNull(result)
    }

    /**
     * CP-37.1: decodeBase64ToBitmap_handles_empty_string
     * Valida que una cadena vacía sea procesada de forma segura sin provocar excepciones.
     */
    @Test
    fun decodeBase64ToBitmap_handles_empty_string() {
        val result = "".decodeBase64ToBitmap()
        assertNull(result)
    }
}
