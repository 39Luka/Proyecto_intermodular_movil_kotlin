package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.model.RegisterUiState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue, errorMessage = "") }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, errorMessage = "") }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, errorMessage = "") }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, errorMessage = "") }
    }

    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value
        when {
            state.username.isBlank() -> _uiState.update { it.copy(errorMessage = "El nombre de usuario es obligatorio") }
            state.email.isBlank() -> _uiState.update { it.copy(errorMessage = "El email es obligatorio") }
            state.password.isBlank() -> _uiState.update { it.copy(errorMessage = "La contraseña es obligatoria") }
            state.confirmPassword.isBlank() -> _uiState.update { it.copy(errorMessage = "Debes repetir la contraseña") }
            state.password != state.confirmPassword -> _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            else -> {
                _uiState.update { it.copy(isLoading = true, errorMessage = "") }
                viewModelScope.launch {
                    try {
                        authRepository.register(state.email, state.password)
                        _uiState.update { it.copy(isLoading = false) }
                        onSuccess()
                    } catch (e: Exception) {
                        _uiState.update { it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Error al registrarse"
                        ) }
                    }
                }
            }
        }
    }
}
