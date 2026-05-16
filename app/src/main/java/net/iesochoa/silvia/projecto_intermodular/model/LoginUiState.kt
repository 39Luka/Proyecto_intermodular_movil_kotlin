package net.iesochoa.silvia.projecto_intermodular.model

/** Estado del inicio de sesión. */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
