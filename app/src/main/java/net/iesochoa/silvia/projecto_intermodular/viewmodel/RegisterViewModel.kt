package net.iesochoa.silvia.projecto_intermodular.viewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.model.RegisterUiState

class RegisterViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState


    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue) }
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
                _uiState.update { it.copy(errorMessage = "") }
                onSuccess()  // <--- llama la lambda pasada desde la pantalla
            }
        }
    }


}