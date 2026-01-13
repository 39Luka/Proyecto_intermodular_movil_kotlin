package net.iesochoa.silvia.projecto_intermodular.model

import android.os.Message

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = ""
)
