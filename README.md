# 🥐 La Croassantina - Aplicación Móvil

Una aplicación de e-commerce para una panadería desarrollada en **Android** con **Kotlin** y **Jetpack Compose**, siguiendo arquitectura limpia y patrones de diseño actuales.

## 📋 Descripción General

**La Croassantina** es una aplicación móvil que permite a los usuarios:
- Autenticarse y gestionar su perfil
- Explorar un catálogo de productos
- Filtrar productos por categorías
- Visualizar ofertas especiales
- Agregar productos al carrito de compras
- Realizar compras
- Ver el historial de compras
- Cambiar contraseña y foto de perfil

## 🏗️ Arquitectura

La aplicación sigue la arquitectura **MVVM (Model-View-ViewModel)** con separación de responsabilidades:

```
app/src/main/
├── java/net/iesochoa/silvia/projecto_intermodular/
│   ├── viewmodel/              # ViewModels con lógica de presentación
│   ├── ui/
│   │   ├── screens/            # Pantallas (composables principales)
│   │   ├── components/         # Componentes reutilizables
│   │   ├── navigation/         # Sistema de navegación
│   │   ├── theme/              # Tema y estilos de la app
│   │   └── utils/              # Utilidades para UI
│   ├── data/
│   │   ├── Repository.kt       # Capa de datos (lógica de negocio)
│   │   ├── CartRepository.kt   # Gestión del carrito
│   │   ├── ApiService.kt       # Cliente HTTP (Retrofit)
│   │   ├── TokenManager.kt     # Gestión de tokens JWT
│   │   └── Models.kt           # Modelos de datos
│   ├── model/                  # UI State classes
│   ├── utils/                  # Utilidades globales
│   └── MainApplication.kt      # Inicialización con Hilt
```

## 🛠️ Stack Tecnológico

### Core Framework
- **Android API 26+** (compileSdk 36)
- **Kotlin** con JVM target 11
- **Jetpack Compose** - UI declarativa moderna

### UI y Navegación
- **Compose Material3** - Componentes de diseño
- **Compose Navigation** - Sistema de navegación
- **Coil** - Carga y caché de imágenes

### Arquitectura y Dependency Injection
- **Hilt (Dagger 2)** - Inyección de dependencias
- **ViewModel** - Gestión de estado
- **StateFlow/Flow** - Reactividad

### Networking y Serialización
- **Retrofit 2** - Cliente HTTP
- **GSON** - Serialización JSON
- **OkHttp** - Cliente HTTP subyacente (con interceptor de logs)

### Persistencia
- **DataStore** - Preferencias seguras (reemplaza SharedPreferences)

### Concurrencia
- **Kotlin Coroutines** - Programación asincrónica

### Testing
- **JUnit** - Testing unitario
- **MockK** - Mocking en Kotlin
- **Turbine** - Testing de Flows
- **Espresso** - Testing de UI
- **Navigation Testing** - Testing de navegación

## 📱 Pantallas y Funcionalidades

### 🔐 Autenticación
- **Login** - Acceso con email y contraseña
- **Register** - Registro de nuevos usuarios

### 🏠 Home
- Pantalla principal con bienvenida
- Acceso rápido a categorías
- Productos destacados

### 🛒 Catálogo
- Listado de productos con paginación
- Filtro por categorías
- Búsqueda de productos
- Vista de detalles de producto

### 🎁 Ofertas
- Visualización de ofertas especiales
- Filtrado de ofertas por tipo

### 🛍️ Carrito
- Agregar/eliminar productos
- Cambiar cantidades
- Resumen de compra
- Procesar pedido

### 📦 Compras
- Historial de compras realizadas
- Detalles de cada compra
- Estado de pedidos

### 👤 Perfil
- Información del usuario
- Cambio de contraseña
- Cambio de foto de perfil
- Cierre de sesión

## 🔑 Características Principales

### 🔐 Seguridad
- **JWT (JSON Web Tokens)** para autenticación
- **DataStore** para almacenamiento seguro de tokens
- Validación de entradas de usuario
- Manejo seguro de credenciales

### 🎨 Interfaz de Usuario
- Diseño moderno con Jetpack Compose
- Tema personalizado (Material Design 3)
- Componentes reutilizables
- Respuesta a interacciones del usuario

### 📊 Gestión de Estado
- **StateFlow** para estados reactivos
- **ViewModel** con ciclo de vida consciente
- Manejo de casos de carga, error y éxito

### 🌐 Conectividad
- Comunicación REST con servidor backend
- Interceptores para headers JWT
- Manejo de errores HTTP
- Reintentos en conexión

### 📦 Inyección de Dependencias
- Configuración centralizada en **AppModule**
- Inyección automática con Hilt
- Facilita testing y mantenimiento

## 📋 Modelos de Datos

Modelos reales de mi implementación:

### AuthRequest (Solicitud de autenticación)
```kotlin
data class AuthRequest(
    val email: String,
    val password: String
)
```

### AuthResponse (Respuesta de autenticación)
```kotlin
data class AuthResponse(
    val token: String? = null,
    val refreshToken: String? = null,
    val accessToken: String? = null,
    val jwt: String? = null
)
```

### User (Usuario del sistema)
```kotlin
data class User(
    val id: Int = 0,
    val email: String? = null,
    val name: String? = null,
    val role: String? = "USER",
    val enabled: Boolean = true,
    val profileImageBase64: String? = null
)
```

### Product (Producto del catálogo)
```kotlin
data class Product(
    val id: Int,
    val name: String? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = 0.0,
    val stock: Int? = 0,
    val image: String? = null,
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val category: Category? = null,
    val categoryId: Int? = null,
    val active: Boolean = true
)
```

### Category (Categoría de productos)
```kotlin
data class Category(
    val id: Int = 0,
    val name: String? = null
)
```

### Promotion (Promoción o descuento)
```kotlin
data class Promotion(
    val id: Int,
    val description: String? = null,
    val productId: Int,
    val productName: String? = null,
    val discountPercentage: Double? = 0.0,
    val startDate: String? = null,
    val endDate: String? = null,
    val active: Boolean = true,
    val used: Boolean = false
)
```

### CartItem (Item del carrito)
```kotlin
data class CartItem(
    val product: Product,
    val quantity: Int,
    val price: Double = 0.0,
    val discount: Double = 0.0
)
```

### Purchase (Compra realizada)
```kotlin
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
```

### PurchaseItem (Línea de compra)
```kotlin
data class PurchaseItem(
    val id: Int? = null,
    val productId: Int,
    val productName: String? = null,
    val quantity: Int? = 0,
    val subtotal: Double? = 0.0,
    val unitPrice: Double? = 0.0
)
```

### CreatePurchaseRequest (Solicitud para crear compra)
```kotlin
data class CreatePurchaseRequest(
    val items: List<PurchaseItemRequest>,
    val userId: Int? = null
)
```

### PagedResponse (Respuesta paginada)
```kotlin
data class PagedResponse<T>(
    val content: List<T> = emptyList(),
    val totalPages: Int = 0,
    val totalElements: Int = 0,
    val number: Int = 0
)
```

## 🚀 Configuración e Instalación

### Requisitos
- Android Studio Arctic Fox o superior
- JDK 11+
- Android SDK 26+ instalado
- Gradle 8.x

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <URL del repositorio>
   cd Proyecto_intermodular_movil_kotlin
   ```

2. **Abrir en Android Studio**
   - File → Open → Seleccionar la carpeta del proyecto

3. **Configurar Backend**
   - Actualizar la URL del API en `ApiService.kt` (según tu servidor)
   - Asegurar que el servidor backend esté ejecutándose en la URL configurada

4. **Compilar y ejecutar**
   ```bash
   # En Android Studio o terminal
   ./gradlew build
   ./gradlew installDebug
   ```

## 📝 API Endpoints

Estos son los endpoints de La Croassantina. Pueden variar según la configuración del backend:

| Método | Endpoint | Parámetros | Descripción |
|--------|----------|-----------|-------------|
| POST | `/auth/login` | email, password | Login de usuario |
| POST | `/auth/register` | email, password | Registro de usuario |
| PATCH | `/auth/me/profile-image` | profileImageBase64 | Cambiar foto de perfil |
| PATCH | `/auth/me/password` | currentPassword, newPassword | Cambiar contraseña |
| GET | `/products` | categoryId, name, page, size, sort | Listar productos con filtros |
| GET | `/products/{id}` | - | Obtener detalles de un producto |
| GET | `/products/top-selling` | page, size | Productos más vendidos |
| GET | `/categories` | page, size | Listar categorías |
| GET | `/promotions/active` | productId, userId, page, size | Promociones activas |
| GET | `/promotions` | page, size | Todas las promociones |
| GET | `/promotions/available` | userId, page, size | Promociones disponibles |
| GET | `/purchases` | page, size, userId, startDate, endDate | Historial de compras |
| GET | `/purchases/{id}` | - | Detalles de una compra |
| POST | `/purchases` | items (con productId, quantity, promotionId) | Crear compra |
| PATCH | `/purchases/{id}/pay` | - | Pagar una compra |
| PATCH | `/purchases/{id}/cancel` | - | Cancelar una compra |
| GET | `/users` | email | Obtener usuario por email |
| GET | `/users/{id}` | - | Obtener usuario por ID |

## 🧪 Testing

La aplicación incluye tests unitarios y de integración:

```bash
# Tests unitarios
./gradlew test

# Tests de instrumentación (Android)
./gradlew connectedAndroidTest

# Cobertura de tests
./gradlew testDebugUnitTestCoverage
```

### Herramientas de Testing
- **JUnit 4** - Framework base
- **MockK** - Mocking de dependencias
- **Turbine** - Testing de Flows
- **Espresso** - Testing de UI
- **Hilt Testing** - Testing con inyección de dependencias

## 🔧 Configuración del Build

### Versiones
- compileSdk: 36 (Android 15)
- minSdk: 26 (Android 8.0)
- targetSdk: 36 (Android 15)

### Plugins
- Android Application Plugin
- Kotlin Android Plugin
- Compose Compiler Plugin
- Hilt (Dagger) Plugin
- KSP (Kotlin Symbol Processing) Plugin

## 📦 Dependencias Principales

### Jetpack
```
androidx.lifecycle:lifecycle-viewmodel-compose:2.x
androidx.compose:compose-bom:2024.x
androidx.compose.material3:material3
androidx.navigation:navigation-compose:2.x
androidx.datastore:datastore-preferences:1.0.0
```

### HTTP & Serialización
```
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.retrofit2:converter-gson:2.9.0
com.google.code.gson:gson:2.10.1
com.squareup.okhttp3:okhttp:4.11.0
```

### Inyección de Dependencias
```
com.google.dagger:hilt-android:2.51.1
androidx.hilt:hilt-navigation-compose:1.2.0
```

### Imágenes
```
io.coil-kt:coil-compose:2.5.0
```

## 📚 Estructura de Carpetas Detallada

### `viewmodel/`
Contiene todos los ViewModels que manejan la lógica de presentación:
- `LoginViewModel.kt` - Lógica de login
- `CartViewModel.kt` - Gestión del carrito
- `ProductViewModel.kt` - Listado de productos
- `PurchaseViewModel.kt` - Gestión de compras
- Y más...

### `ui/screens/`
Pantallas composables principales:
- `LoginScreen.kt`
- `HomeScreen.kt`
- `CatalogScreen.kt`
- `CartScreen.kt`
- `ProfileScreen.kt`
- Y más...

### `ui/components/`
Componentes reutilizables:
- `PrimaryButton.kt` - Botón principal
- `SearchBar.kt` - Barra de búsqueda
- `CustomCard.kt` - Tarjeta personalizada
- `BottomBar.kt` - Barra de navegación inferior
- Y más...

### `data/`
Capa de datos:
- `ApiService.kt` - Interfaz Retrofit para llamadas API
- `Repository.kt` - Lógica de acceso a datos
- `CartRepository.kt` - Gestión local del carrito
- `TokenManager.kt` - Manejo de autenticación JWT
- `Models.kt` - Data classes de la API

### `ui/theme/`
Configuración de tema:
- `Theme.kt` - Tema principal
- `Color.kt` - Paleta de colores
- `Type.kt` - Tipografía
- `Dimens.kt` - Dimensiones reutilizables

## 📄 Información del Proyecto

- **Título**: La Croassantina
- **Tipo**: Proyecto Intermodular (TFG)
- **Desarrollador**: Silvia Cachón Leiva
- **Centro**: IES Severo Ochoa Elche

## 🎯 Próximas Mejoras

- [ ] Agregar preferencias de usuario
- [ ] Implementar filtros avanzados
- [ ] Soporte para múltiples métodos de pago
- [ ] Sistema de reseñas y calificaciones
- [ ] Notificaciones push
- [ ] Modo oscuro mejorado
- [ ] Sincronización en tiempo real

## 📖 Recursos Útiles

- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Guía de Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

---

**Versión**: 1.0  
**Última actualización**: Mayo 2026
