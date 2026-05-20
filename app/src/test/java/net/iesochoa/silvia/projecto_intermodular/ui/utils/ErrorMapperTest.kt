package net.iesochoa.silvia.projecto_intermodular.ui.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.ConnectException
import java.net.UnknownHostException

class ErrorMapperTest {

    /**
     * CP-23: map_handles_connection_errors
     * Valida que los errores de conexión se traduzcan en mensajes comprensibles sobre el estado del internet.
     */
    @Test
    fun `map handles connection errors`() {
        val e1 = UnknownHostException()
        val result1 = ErrorMapper.map(e1, "Default")
        assertEquals("No se pudo conectar al servidor. Revisa tu conexión a internet.", result1)

        val e2 = ConnectException()
        val result2 = ErrorMapper.map(e2, "Default")
        assertEquals("No se pudo conectar al servidor. Revisa tu conexión a internet.", result2)
    }

    /**
     * CP-23.1: map_uses_default_message_for_unknown_exceptions
     * Comprueba que si la excepción es desconocida, se utilice el mensaje de error por defecto o el mensaje de la excepción.
     */
    @Test
    fun `map uses default message for unknown exceptions`() {
        val e = Exception("Technical error")
        val result = ErrorMapper.map(e, "Friendly default")
        assertEquals("Technical error", result)
    }
}
