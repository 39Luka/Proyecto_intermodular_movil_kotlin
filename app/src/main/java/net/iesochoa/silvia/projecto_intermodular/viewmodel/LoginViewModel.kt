package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.model.LoginUiState
import net.iesochoa.silvia.projecto_intermodular.ui.utils.ErrorMapper
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica de autenticación para el inicio de sesión.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, errorMessage = "") }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, errorMessage = "") }
    }

    /** Intenta iniciar sesión con las credenciales actuales. */
    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email y contraseña son obligatorios") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = "") }
        viewModelScope.launch {
            try {
                authRepository.login(state.email, state.password)
                _uiState.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = ErrorMapper.map(e, "Error al iniciar sesión")
                ) }
            }
        }
    }
}
