package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun onNewPasswordChange(newValue: String) {
        _uiState.update { it.copy(newPassword = newValue) }
    }

    fun onRepeatPasswordChange(newValue: String) {
        _uiState.update { it.copy(repeatPassword = newValue) }
    }

    fun onNewImagePathChange(newValue: String) {
        _uiState.update { it.copy(newImagePath = newValue) }
    }

    fun changePassword(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.newPassword.isNotEmpty() && state.newPassword == state.repeatPassword) {
            onSuccess()
            _uiState.update { it.copy(newPassword = "", repeatPassword = "") }
        }
    }

    fun changeImage(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.newImagePath.isNotEmpty()) {
            onSuccess()
            _uiState.update { it.copy(profileImage = state.newImagePath, newImagePath = "") }
        }
    }

    fun logout(onLogout: () -> Unit) = onLogout()
}
