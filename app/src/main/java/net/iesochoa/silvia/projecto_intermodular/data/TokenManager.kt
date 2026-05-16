package net.iesochoa.silvia.projecto_intermodular.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestor del token de autenticación (JWT).
 */
@Singleton
class TokenManager @Inject constructor(private val context: Context) {

    /** Flujo observable que emite el token de autenticación actual. */
    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.AUTH_TOKEN]
    }

    /** Guarda el token de autenticación en la persistencia local. */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.AUTH_TOKEN] = token
        }
    }

    /** Elimina el token de autenticación guardado. */
    suspend fun deleteAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.AUTH_TOKEN)
        }
    }

    /** Obtiene el token de autenticación de forma bloqueante. */
    fun getAuthTokenBlocking(): String? = runBlocking {
        authToken.first()
    }
}
