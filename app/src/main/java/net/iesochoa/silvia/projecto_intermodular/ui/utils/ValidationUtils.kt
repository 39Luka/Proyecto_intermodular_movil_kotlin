package net.iesochoa.silvia.projecto_intermodular.ui.utils

/**
 * Utilidades para la validación de campos de entrada en la aplicación.
 */
object ValidationUtils {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    /**
     * Verifica si un correo electrónico tiene un formato válido.
     */
    fun isValidEmail(email: String): Boolean {
        return email.matches(EMAIL_REGEX)
    }

    /**
     * Verifica si una contraseña cumple con los requisitos mínimos de seguridad.
     */
    fun isValidPassword(password: String): Boolean {
        return password.length in 8..72
    }
}
