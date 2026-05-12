package net.iesochoa.silvia.projecto_intermodular.data

import com.google.gson.annotations.SerializedName

// Auth Models
data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String? = null,
    val refreshToken: String? = null,
    val accessToken: String? = null,
    val jwt: String? = null
) {
    fun getAuthToken(): String = token ?: accessToken ?: jwt ?: ""
}

data class User(
    val id: Int = 0,
    val email: String? = null,
    val name: String? = null,
    val role: String? = "USER",
    val enabled: Boolean = true,
    @SerializedName("profileImageBase64", alternate = ["profileImage", "profile_image", "imageBase64", "image_base64", "foto", "image", "profile_image_base64"])
    val profileImageBase64: String? = null
) {
    fun getDisplayProfileImage(): String? {
        if (!profileImageBase64.isNullOrEmpty()) {
            return "data:image/jpeg;base64,$profileImageBase64"
        }
        return null
    }
}

data class UpdateProfileImageRequest(
    @SerializedName("profileImageBase64")
    val profileImageBase64: String?
)

data class ChangePasswordRequest(
    @SerializedName("currentPassword", alternate = ["current"])
    val currentPassword: String,
    @SerializedName("newPassword", alternate = ["new"])
    val newPassword: String
)

// Product Models
data class Product(
    val id: Int,
    @SerializedName("name", alternate = ["nombre"])
    val name: String? = null,
    val title: String? = null,
    @SerializedName("description", alternate = ["descripcion"])
    val description: String? = null,
    @SerializedName("price", alternate = ["precio"])
    val price: Double? = 0.0,
    val stock: Int? = 0,
    val image: String? = null,
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val category: Category? = null,
    val categoryId: Int? = null,
    val active: Boolean = true
) {
    fun getDisplayImage(): String? {
        if (!imageUrl.isNullOrEmpty()) return imageUrl
        if (!image.isNullOrEmpty()) return image
        if (!imageBase64.isNullOrEmpty()) {
            return "data:image/jpeg;base64,$imageBase64"
        }
        return null
    }
    fun getDisplayTitle(): String = (title ?: name ?: "Producto").ifEmpty { "Producto" }
    fun getCategoryName(): String = category?.name ?: "Sin categoría"
}

data class TopSellingProduct(
    val productId: Int,
    val productName: String? = null,
    val totalQuantity: Int = 0
)

data class Category(
    val id: Int = 0,
    val name: String? = null
)

// Promotion Models
data class Promotion(
    val id: Int,
    @SerializedName("description", alternate = ["descripcion"])
    val description: String? = null,
    val productId: Int,
    val productName: String? = null,
    val discountPercentage: Double? = 0.0,
    val startDate: String? = null,
    val endDate: String? = null,
    val active: Boolean = true
)

// Cart Models
data class CartItem(
    val product: Product,
    val quantity: Int,
    val price: Double = 0.0,
    val discount: Double = 0.0
)

// Purchase Models
data class Purchase(
    val id: Int,
    val userId: Int,
    val status: String? = "CREATED",
    val total: Double? = 0.0,
    val subtotal: Double? = 0.0,
    val discount: Double? = 0.0,
    val address: String? = null,
    val phoneNumber: String? = null,
    val createdAt: String? = null,
    val items: List<PurchaseItem> = emptyList()
)

data class PurchaseItem(
    val id: Int? = null,
    val productId: Int,
    val productName: String? = null,
    val quantity: Int? = 0,
    val subtotal: Double? = 0.0,
    val unitPrice: Double? = 0.0
)

data class CreatePurchaseRequest(
    val items: List<PurchaseItemRequest>,
    val userId: Int? = null
)

data class PurchaseItemRequest(
    val productId: Int,
    val quantity: Int,
    val promotionId: Int? = null
)

// API Response Wrappers
data class PagedResponse<T>(
    val content: List<T> = emptyList(),
    val totalPages: Int = 0,
    val totalElements: Int = 0,
    val number: Int = 0
)
