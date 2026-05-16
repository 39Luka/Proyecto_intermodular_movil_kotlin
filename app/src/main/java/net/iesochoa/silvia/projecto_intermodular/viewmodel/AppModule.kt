package net.iesochoa.silvia.projecto_intermodular.viewmodel

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.iesochoa.silvia.projecto_intermodular.data.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para provisión de dependencias de red.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    private const val BASE_URL = "https://proyectointermodularapi-production.up.railway.app/"

    /** Proporciona un interceptor para añadir el token JWT a las peticiones. */
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val path = originalRequest.url.encodedPath
            
            if (path.contains("/auth/login") || path.contains("/auth/register")) {
                return@Interceptor chain.proceed(originalRequest)
            }

            val token = tokenManager.getAuthTokenBlocking()
            val requestBuilder = originalRequest.newBuilder()
            
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
    }

    /** Configura el cliente HTTP con interceptores y tiempos de espera. */
    @Provides
    @Singleton
    fun provideHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    /** Proporciona la instancia configurada de Retrofit. */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** Proporciona el servicio de acceso a la API. */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /** Proporciona el gestor de tokens. */
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
}

/**
 * Módulo Dagger Hilt para provisión de repositorios de datos.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        apiService: ApiService,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepository(context, apiService, tokenManager)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepository(apiService)
    }

    @Provides
    @Singleton
    fun providePromotionRepository(apiService: ApiService): PromotionRepository {
        return PromotionRepository(apiService)
    }

    @Provides
    @Singleton
    fun providePurchaseRepository(apiService: ApiService): PurchaseRepository {
        return PurchaseRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(apiService: ApiService): CartRepository {
        return CartRepository(apiService)
    }
}
