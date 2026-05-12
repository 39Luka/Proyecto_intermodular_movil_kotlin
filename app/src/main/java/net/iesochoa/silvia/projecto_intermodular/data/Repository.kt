package net.iesochoa.silvia.projecto_intermodular.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.iesochoa.silvia.projecto_intermodular.utils.JwtUtils

val Context.dataStore by preferencesDataStore(name = "app_prefs")

object PreferenceKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ID = stringPreferencesKey("user_id")
    val USER_ROLE = stringPreferencesKey("user_role")
    val USER_IMAGE = stringPreferencesKey("user_image")
}

class AuthRepository(
    private val context: Context,
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    suspend fun login(email: String, password: String): User {
        // 1. Limpieza absoluta antes de entrar
        logout()
        
        val response = apiService.login(AuthRequest(email, password))
        val token = response.getAuthToken()
        if (token.isEmpty()) throw Exception("El servidor no devolvió un token")

        tokenManager.saveAuthToken(token)
        response.refreshToken?.let { 
            context.dataStore.edit { prefs -> prefs[PreferenceKeys.REFRESH_TOKEN] = it }
        }
        
        // 2. Intentar obtener el perfil con imagen inmediatamente
        var user = JwtUtils.decodeUser(token, email)
        try {
            val profile = apiService.getUserByEmail(email)
            user = profile
            android.util.Log.d("AuthRepo", "Login exitoso, imagen recuperada")
        } catch (e: Exception) {
            android.util.Log.e("AuthRepo", "Error recuperando perfil completo: ${e.message}")
        }

        saveUserLocal(user)
        return user
    }

    suspend fun register(email: String, password: String): User {
        val response = apiService.register(AuthRequest(email, password))
        val token = response.getAuthToken()
        tokenManager.saveAuthToken(token)
        
        val user = JwtUtils.decodeUser(token, email)
        saveUserLocal(user)
        return user
    }

    suspend fun updateProfileImage(base64Image: String?) {
        val updatedUser = apiService.updateProfileImage(UpdateProfileImageRequest(base64Image))
        
        // Si el servidor devuelve el usuario pero sin la imagen (o vacía), 
        // usamos la que acabamos de subir para actualizar el estado local.
        val userToSave = if (updatedUser.profileImageBase64.isNullOrEmpty() && !base64Image.isNullOrEmpty()) {
            updatedUser.copy(profileImageBase64 = base64Image)
        } else {
            updatedUser
        }
        
        saveUserLocal(userToSave)
    }

    suspend fun changePassword(current: String, new: String) {
        apiService.changePassword(ChangePasswordRequest(current, new))
        // Al cambiar contraseña el backend suele invalidar tokens, 
        // pero aquí dependerá de si el backend devuelve un nuevo token o no.
    }

    private suspend fun saveUserLocal(user: User) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_EMAIL] = user.email ?: ""
            preferences[PreferenceKeys.USER_ID] = user.id.toString()
            preferences[PreferenceKeys.USER_ROLE] = user.role ?: "USER"
            
            // Guardar imagen solo si el servidor la envía (evita borrar la local con nulos)
            if (!user.profileImageBase64.isNullOrEmpty()) {
                val cleanImage = user.profileImageBase64.trim()
                    .replace("\n", "").replace("\r", "")
                preferences[PreferenceKeys.USER_IMAGE] = cleanImage
                android.util.Log.d("AuthRepo", "Imagen persistida en DataStore")
            }
        }
    }

    fun getUser(): Flow<User?> {
        return context.dataStore.data.map { preferences ->
            val email = preferences[PreferenceKeys.USER_EMAIL] ?: return@map null
            val id = preferences[PreferenceKeys.USER_ID]?.toIntOrNull() ?: 0
            val role = preferences[PreferenceKeys.USER_ROLE] ?: "USER"
            val image = preferences[PreferenceKeys.USER_IMAGE] ?: ""
            User(id = id, email = email, role = role, profileImageBase64 = image)
        }
    }

    suspend fun logout() {
        tokenManager.deleteAuthToken()
        context.dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.REFRESH_TOKEN)
            preferences.remove(PreferenceKeys.USER_EMAIL)
            preferences.remove(PreferenceKeys.USER_ID)
            preferences.remove(PreferenceKeys.USER_ROLE)
            preferences.remove(PreferenceKeys.USER_IMAGE)
        }
    }
}

class ProductRepository(private val apiService: ApiService) {
    
    suspend fun getProducts(categoryId: Int? = null, page: Int = 0, size: Int = 12): PagedResponse<Product> {
        return apiService.getProducts(categoryId, page, size)
    }

    suspend fun getProductById(id: Int): Product {
        return apiService.getProductById(id)
    }

    suspend fun getTopSellingProducts(size: Int = 6): List<Product> {
        return try {
            val response = apiService.getTopSellingProducts(0, size)
            val items = response.content
            items.mapNotNull { item ->
                try {
                    apiService.getProductById(item.productId)
                } catch (e: Exception) { null }
            }.ifEmpty { 
                apiService.getProducts(null, 0, size).content
            }
        } catch (e: Exception) {
            try {
                apiService.getProducts(null, 0, size).content
            } catch (e2: Exception) {
                emptyList()
            }
        }
    }
}

class CategoryRepository(private val apiService: ApiService) {
    suspend fun getCategories(): List<Category> {
        return apiService.getCategories().content
    }
}

class PromotionRepository(private val apiService: ApiService) {
    suspend fun getActivePromotions(productId: Int, userId: Int? = null): List<Promotion> {
        return apiService.getActivePromotions(productId, userId).content
    }

    suspend fun getAllPromotions(): List<Promotion> {
        return apiService.getAllPromotions().content
    }
}

class PurchaseRepository(private val apiService: ApiService) {
    suspend fun getPurchases(userId: Int? = null): List<Purchase> {
        return apiService.getPurchases(userId = userId).content
    }

    suspend fun getPurchaseById(id: Int): Purchase {
        return apiService.getPurchaseById(id)
    }

    suspend fun createPurchase(items: List<PurchaseItemRequest>, userId: Int? = null): Purchase {
        return apiService.createPurchase(CreatePurchaseRequest(items, userId))
    }

    suspend fun payPurchase(id: Int) {
        apiService.payPurchase(id)
    }

    suspend fun cancelPurchase(id: Int) {
        apiService.cancelPurchase(id)
    }
}
