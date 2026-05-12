package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.model.ProfileUiState
import java.io.ByteArrayOutputStream
import java.io.InputStream
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
                    _uiState.update { it.copy(
                        username = user.name ?: user.email?.split("@")?.get(0) ?: "Usuario",
                        email = user.email ?: "",
                        profileImage = user.profileImageBase64
                    ) }
                }
            }
        }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri?.toString(), errorMessage = null) }
    }

    fun onCurrentPasswordChange(newValue: String) {
        _uiState.update { it.copy(currentPassword = newValue, errorMessage = null) }
    }

    fun onNewPasswordChange(newPassword: String) {
        _uiState.update { it.copy(newPassword = newPassword, errorMessage = null) }
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        _uiState.update { it.copy(repeatPassword = repeatPassword, errorMessage = null) }
    }

    fun updateImage(contentResolver: ContentResolver, onSuccess: () -> Unit) {
        val selectedUri = _uiState.value.selectedImageUri
        
        if (selectedUri == null) {
            _uiState.update { it.copy(errorMessage = "Por favor, selecciona una imagen") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch {
            try {
                val base64Image = withContext(Dispatchers.IO) {
                    uriToBase64(contentResolver, Uri.parse(selectedUri))
                }
                
                if (base64Image != null) {
                    authRepository.updateProfileImage(base64Image)
                    
                    // Actualizamos el estado local inmediatamente para que la UI cambie ya
                    _uiState.update { it.copy(
                        isLoading = false,
                        successMessage = "Imagen actualizada correctamente",
                        profileImage = base64Image,
                        selectedImageUri = null
                    ) }

                    onSuccess()
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Error al procesar la imagen") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al actualizar imagen") }
            }
        }
    }

    private fun uriToBase64(contentResolver: ContentResolver, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            // Reducimos un poco la calidad para ahorrar espacio y evitar errores 413
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            val byteArray = outputStream.toByteArray()
            // Usamos NO_WRAP para evitar saltos de línea que rompen el JSON de la API
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }

    fun updatePassword(onSuccess: () -> Unit) {
        val state = _uiState.value
        when {
            state.currentPassword.isBlank() -> 
                _uiState.update { it.copy(errorMessage = "La contraseña actual es necesaria") }
            state.newPassword.length < 8 -> 
                _uiState.update { it.copy(errorMessage = "La nueva contraseña debe tener al menos 8 caracteres") }
            state.newPassword != state.repeatPassword -> 
                _uiState.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            else -> {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                viewModelScope.launch {
                    try {
                        authRepository.changePassword(state.currentPassword, state.newPassword)
                        _uiState.update { it.copy(
                            isLoading = false, 
                            successMessage = "Contraseña cambiada con éxito",
                            currentPassword = "",
                            newPassword = "",
                            repeatPassword = ""
                        ) }
                        // Navegar después de un pequeño delay para que el usuario vea el mensaje
                        kotlinx.coroutines.delay(1000)
                        onSuccess()
                        // Limpiar mensaje después de navegar
                        _uiState.update { it.copy(successMessage = null) }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al cambiar contraseña") }
                    }
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutSuccess()
        }
    }
}
