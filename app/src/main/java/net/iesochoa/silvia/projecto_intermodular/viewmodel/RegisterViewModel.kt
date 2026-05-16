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
import net.iesochoa.silvia.projecto_intermodular.ui.utils.ErrorMapper
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica de registro de nuevos usuarios.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, errorMessage = "") }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, errorMessage = "") }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, errorMessage = "") }
    }

    /** Valida los datos e intenta registrar al nuevo usuario. */
    fun register(onSuccess: () -> Unit) {
        val state = _uiState.value
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        when {
            state.email.isBlank() -> 
                _uiState.update { it.copy(errorMessage = "El email es obligatorio") }
            !state.email.matches(emailRegex) -> 
                _uiState.update { it.copy(errorMessage = "El formato del email no es válido") }
            state.password.isBlank() -> 
                _uiState.update { it.copy(errorMessage = "La contraseña es obligatoria") }
            state.password.length < 8 -> 
                _uiState.update { it.copy(errorMessage = "La contraseña debe tener al menos 8 caracteres") }
            state.password.length > 72 -> 
                _uiState.update { it.copy(errorMessage = "La contraseña no puede superar los 72 caracteres") }
            state.confirmPassword.isBlank() -> 
                _uiState.update { it.copy(errorMessage = "Debes repetir la contraseña") }
            state.password != state.confirmPassword -> 
                _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
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
                            errorMessage = ErrorMapper.map(e, "Error al registrarse")
                        ) }
                    }
                }
            }
        }
    }
}
