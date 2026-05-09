package net.iesochoa.silvia.projecto_intermodular.model

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
