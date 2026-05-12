package net.iesochoa.silvia.projecto_intermodular.data

import retrofit2.http.*

interface ApiService {
    // Auth Endpoints
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @PATCH("auth/me/profile-image")
    suspend fun updateProfileImage(@Body request: UpdateProfileImageRequest): User

    @PATCH("auth/me/password")
    suspend fun changePassword(@Body request: ChangePasswordRequest)

    // Product Endpoints
    @GET("products")
    suspend fun getProducts(
        @Query("categoryId") categoryId: Int? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 12
    ): PagedResponse<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products/top-selling")
    suspend fun getTopSellingProducts(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 6
    ): PagedResponse<TopSellingProduct>

    // Category Endpoints
    @GET("categories")
    suspend fun getCategories(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): PagedResponse<Category>

    // Promotion Endpoints
    @GET("promotions/active")
    suspend fun getActivePromotions(
        @Query("productId") productId: Int,
        @Query("userId") userId: Int? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): PagedResponse<Promotion>

    @GET("promotions")
    suspend fun getAllPromotions(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50
    ): PagedResponse<Promotion>

    // Purchase Endpoints
    @GET("purchases")
    suspend fun getPurchases(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 50,
        @Query("userId") userId: Int? = null
    ): PagedResponse<Purchase>

    @GET("purchases/{id}")
    suspend fun getPurchaseById(@Path("id") id: Int): Purchase

    @POST("purchases")
    suspend fun createPurchase(@Body request: CreatePurchaseRequest): Purchase

    @PATCH("purchases/{id}/pay")
    suspend fun payPurchase(@Path("id") id: Int)

    @PATCH("purchases/{id}/cancel")
    suspend fun cancelPurchase(@Path("id") id: Int)

    // User Endpoints (Admin)
    @GET("users")
    suspend fun getUserByEmail(@Query("email") email: String): User

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User

    @POST("users")
    suspend fun createUser(@Body request: User): User

    @PATCH("users/{id}")
    suspend fun patchUser(@Path("id") id: Int, @Body request: Map<String, Any>): Unit
}
