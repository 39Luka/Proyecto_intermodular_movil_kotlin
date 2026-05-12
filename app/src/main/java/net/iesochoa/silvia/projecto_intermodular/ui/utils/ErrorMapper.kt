package net.iesochoa.silvia.projecto_intermodular.ui.utils

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorMapper {
    fun map(e: Exception, defaultMessage: String): String {
        return when (e) {
            is UnknownHostException, is ConnectException -> 
                "No se pudo conectar al servidor. Revisa tu conexión a internet."
            is SocketTimeoutException -> 
                "El servidor está tardando demasiado en responder. Inténtalo de nuevo más tarde."
            is HttpException -> {
                when (e.code()) {
                    400 -> "Por favor, verifica que el usuario y la contraseña sean correctos."
                    401 -> "Credenciales incorrectas o sesión expirada. Por favor, vuelve a intentarlo."
                    403 -> "No tienes permiso para realizar esta acción."
                    404 -> "No se encontró lo que buscabas en el servidor."
                    409 -> "Ya existe un registro con esos datos (el email podría estar en uso)."
                    422 -> "Los datos introducidos no son válidos. Por favor, revísalos."
                    in 500..599 -> "Estamos teniendo problemas técnicos en el servidor. Lo sentimos."
                    else -> "Ha ocurrido un error inesperado (Código: ${e.code()})."
                }
            }
            else -> e.localizedMessage ?: defaultMessage
        }
    }
}
