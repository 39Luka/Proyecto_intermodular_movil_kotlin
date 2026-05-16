package net.iesochoa.silvia.projecto_intermodular.model

/** Estado del registro de nuevos usuarios. */
data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
