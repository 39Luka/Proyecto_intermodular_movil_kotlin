package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                if (user != null) {
                    _uiState.update { it.copy(username = user.name ?: user.email ?: "Usuario") }
                }
            }
        }
    }

    fun onImagePathChange(newPath: String) {
        _uiState.update { it.copy(newImagePath = newPath) }
    }

    fun onNewPasswordChange(newPassword: String) {
        _uiState.update { it.copy(newPassword = newPassword) }
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        _uiState.update { it.copy(repeatPassword = repeatPassword) }
    }

    fun updateImage(onSuccess: () -> Unit) {
        // En una app real llamaríamos a un servicio de usuario
        onSuccess()
    }

    fun updatePassword(onSuccess: () -> Unit) {
        if (_uiState.value.newPassword == _uiState.value.repeatPassword) {
            // En una app real llamaríamos a un servicio de usuario
            onSuccess()
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutSuccess()
        }
    }
}
