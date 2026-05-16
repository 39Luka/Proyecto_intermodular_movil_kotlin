package net.iesochoa.silvia.projecto_intermodular.ui.utils

import retrofit2.HttpException
import org.json.JSONObject
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Utilidad para transformar excepciones técnicas en mensajes de error amigables para el usuario.
 * Maneja errores de red, respuestas HTTP del servidor y mensajes personalizados de la API.
 */
object ErrorMapper {
    /** Mapea una excepción a un mensaje de error amigable para el usuario. */
    fun map(e: Exception, defaultMessage: String): String {
        return when (e) {
            is UnknownHostException, is ConnectException -> 
                "No se pudo conectar al servidor. Revisa tu conexión a internet."
            is SocketTimeoutException -> 
                "El servidor está tardando demasiado en responder. Inténtalo de nuevo más tarde."
            is HttpException -> {
                val serverMessage = try {
                    val body = e.response()?.errorBody()?.string()
                    if (body != null) {
                        val json = JSONObject(body)
                        if (json.has("message")) json.getString("message") else null
                    } else null
                } catch (_: Exception) {
                    null
                }

                if (serverMessage != null && serverMessage.isNotBlank()) return serverMessage

                when (e.code()) {
                    400 -> "Por favor, verifica los datos introducidos."
                    401 -> "Credenciales incorrectas o sesión expirada. Por favor, vuelve a intentarlo."
                    403 -> "No tienes permiso para realizar esta acción."
                    404 -> "No se encontró lo que buscabas en el servidor."
                    409 -> "Conflicto de datos: la operación no pudo completarse por un conflicto en el servidor."
                    422 -> "Los datos introducidos no son válidos. Por favor, revísalos."
                    in 500..599 -> "Estamos teniendo problemas técnicos en el servidor. Lo sentimos."
                    else -> "Ha ocurrido un error inesperado (Código: ${e.code()})."
                }
            }
            else -> e.localizedMessage ?: defaultMessage
        }
    }
}
