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

/**
 * Repositorio de autenticación y persistencia del perfil de usuario.
 */
class AuthRepository(
    private val context: Context,
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    /** Realiza el login del usuario y guarda el token JWT. */
    suspend fun login(email: String, password: String): User {
        logout()
        
        val response = apiService.login(AuthRequest(email, password))
        val token = response.getAuthToken()
        if (token.isEmpty()) throw Exception("El servidor no devolvió un token")

        tokenManager.saveAuthToken(token)
        response.refreshToken?.let { 
            context.dataStore.edit { prefs -> prefs[PreferenceKeys.REFRESH_TOKEN] = it }
        }
        
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

    /** Actualiza la imagen de perfil del usuario. */
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
        val response = apiService.changePassword(ChangePasswordRequest(current, new))
        if (!response.isSuccessful) throw retrofit2.HttpException(response)
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

/**
 * Repositorio para la gestión de productos.
 */
class ProductRepository(private val apiService: ApiService) {
    
    /** Obtiene una lista paginada de productos, opcionalmente filtrada por categoría y nombre. */
    suspend fun getProducts(
        categoryId: Int? = null,
        name: String? = null,
        page: Int = 0,
        size: Int = 12,
        sort: String? = "id,desc"
    ): PagedResponse<Product> {
        val response = apiService.getProducts(categoryId, name, page, size, sort)
        // Mostrar solo productos activos
        return response.copy(content = response.content.filter { it.active })
    }

    /** Busca un producto por su ID único. */
    suspend fun getProductById(id: Int): Product {
        val product = apiService.getProductById(id)
        if (!product.active) throw Exception("Este producto ya no está disponible")
        return product
    }

    /** Obtiene los productos más vendidos del sistema. */
    suspend fun getTopSellingProducts(size: Int = 6): List<Product> {
        return try {
            val response = apiService.getTopSellingProducts(0, size)
            val items = response.content
            items.mapNotNull { item ->
                try {
                    val p = apiService.getProductById(item.productId)
                    if (p.active) p else null
                } catch (e: Exception) { null }
            }.ifEmpty { 
                getProducts(categoryId = null, name = null, page = 0, size = size).content
            }
        } catch (e: Exception) {
            try {
                getProducts(categoryId = null, name = null, page = 0, size = size).content
            } catch (e2: Exception) {
                emptyList()
            }
        }
    }
}

/**
 * Repositorio para la gestión de categorías.
 */
class CategoryRepository(private val apiService: ApiService) {
    /** Obtiene todas las categorías de productos disponibles. */
    suspend fun getCategories(): List<Category> {
        return apiService.getCategories().content
    }
}

/**
 * Repositorio para la gestión de promociones y ofertas.
 */
class PromotionRepository(private val apiService: ApiService) {
    /** Obtiene las promociones activas. Si se pasa productId y/o userId, filtra por ellos. */
    suspend fun getActivePromotions(productId: Int? = null, userId: Int? = null): List<Promotion> {
        return apiService.getActivePromotions(productId, userId).content.filter { it.active }
    }

    /** Obtiene el listado completo de promociones del sistema. */
    suspend fun getAllPromotions(): List<Promotion> {
        return apiService.getAllPromotions().content.filter { it.active }
    }

    /** Obtiene las promociones disponibles (no usadas) para un usuario. */
    suspend fun getAvailablePromotions(userId: Int? = null): List<Promotion> {
        return apiService.getAvailablePromotions(userId).content
    }
}

/**
 * Repositorio para la gestión de compras y pedidos.
 */
class PurchaseRepository(private val apiService: ApiService) {
    /**
     * Obtiene el historial de compras de un usuario con filtros opcionales.
     */
    suspend fun getPurchases(
        userId: Int? = null,
        page: Int = 0,
        pageSize: Int = 10,
        startDate: String? = null,
        endDate: String? = null
    ): PagedResponse<Purchase> {
        return apiService.getPurchases(
            page = page,
            size = pageSize,
            userId = userId,
            startDate = startDate,
            endDate = endDate
        )
    }

    /** Obtiene el detalle de un pedido por su ID. */
    suspend fun getPurchaseById(id: Int): Purchase {
        return apiService.getPurchaseById(id)
    }

    /** Registra un nuevo pedido en el servidor. */
    suspend fun createPurchase(items: List<PurchaseItemRequest>, userId: Int? = null): Purchase {
        return apiService.createPurchase(CreatePurchaseRequest(items, userId))
    }

    /** Marca un pedido como pagado. */
    suspend fun payPurchase(id: Int) {
        val response = apiService.payPurchase(id)
        if (!response.isSuccessful) throw retrofit2.HttpException(response)
    }

    /** Solicita la cancelación de un pedido. */
    suspend fun cancelPurchase(id: Int) {
        val response = apiService.cancelPurchase(id)
        if (!response.isSuccessful) throw retrofit2.HttpException(response)
    }
}
