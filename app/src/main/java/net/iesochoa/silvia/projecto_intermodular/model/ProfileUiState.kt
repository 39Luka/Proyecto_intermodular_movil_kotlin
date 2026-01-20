package net.iesochoa.silvia.projecto_intermodular.model

data class ProfileUiState(
    val username: String = "Nombre Usuario",
    val profileImage: String? = null,
    val newImagePath: String = "",
    val newPassword: String = "",
    val repeatPassword: String = ""
)
