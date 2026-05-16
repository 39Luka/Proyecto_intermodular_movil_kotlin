package net.iesochoa.silvia.projecto_intermodular.model

/**
 * Estado de la interfaz de usuario para las pantallas relacionadas con el perfil.
 * Gestiona tanto la visualización de datos como los formularios de cambio de imagen y contraseña.
 */
data class ProfileUiState(
    val username: String = "Nombre Usuario",
    val email: String = "",
    val profileImage: String? = null,
    val newImagePath: String = "",
    val selectedImageUri: String? = null,
    val currentPassword: String = "",
    val newPassword: String = "",
    val repeatPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
