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

/**
 * ViewModel que gestiona el perfil del usuario.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        observeUser()
    }

    /** Carga los datos iniciales del usuario y los mantiene actualizados. */
    private fun observeUser() {
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

    /** Actualiza la URI de la imagen seleccionada. */
    fun onImageSelected(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri?.toString(), errorMessage = null) }
    }

    /** Actualiza el campo de contraseña actual. */
    fun onCurrentPasswordChange(newValue: String) {
        _uiState.update { it.copy(currentPassword = newValue, errorMessage = null) }
    }

    /** Actualiza el campo de nueva contraseña. */
    fun onNewPasswordChange(newPassword: String) {
        _uiState.update { it.copy(newPassword = newPassword, errorMessage = null) }
    }

    /** Actualiza el campo de repetir contraseña. */
    fun onRepeatPasswordChange(repeatPassword: String) {
        _uiState.update { it.copy(repeatPassword = repeatPassword, errorMessage = null) }
    }

    /** Sube la nueva imagen de perfil al servidor. */
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

    /** Convierte una imagen de la galería (URI) a formato Base64 comprimido. */
    private fun uriToBase64(contentResolver: ContentResolver, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
            val byteArray = outputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }

    /** Cambia la contraseña del usuario tras validar los campos. */
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
                        kotlinx.coroutines.delay(1000)
                        onSuccess()
                        _uiState.update { it.copy(successMessage = null) }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al cambiar contraseña") }
                    }
                }
            }
        }
    }

    /** Limpia los mensajes de error y éxito del estado. */
    fun clearMessages() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

    /** Cierra la sesión del usuario. */
    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutSuccess()
        }
    }
}
