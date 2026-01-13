package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.model.LoginUiState

class LoginViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState


    fun onEmailChange(newEmail: String){
        _uiState.update {
            it.copy(email = newEmail)
        }

    }

    fun onPasswordChange(newPassword: String){
        _uiState.update {
            it.copy(password = newPassword)
        }
    }

    fun login(onSuccess: () -> Unit){
        val state = _uiState.value
        when{
            state.email.isBlank() -> _uiState.update { it.copy(errorMessage = "El email es obligatorio") }
            state.password.isBlank() -> _uiState.update { it.copy(errorMessage = "La contraseña es obligatoria") }

            else -> {
                _uiState.update {  it.copy(errorMessage = "") }
                onSuccess()
            }
        }


    }



}