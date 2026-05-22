---
sidebar_position: 7
---

# 🧩 Módulos

## Descripción general

El proyecto está dividido en **módulos independientes** que se comunican entre sí a través de interfaces bien definidas.

---

## 📦 Core Module

**Ubicación:** `:core`

**Responsabilidad:** Código compartido entre todos los módulos.

**Contenido:**
- Utilidades generales
- Extensiones Kotlin
- Constantes de la app
- Clases base (BaseActivity, BaseFragment, BaseViewModel)

**Ejemplo de uso:**
```kotlin
// Extension desde core
val daysDiff = Date().daysDifferenceFrom(otherDate)
```

---

## 🔐 Auth Module

**Ubicación:** `:feature:auth`

**Responsabilidad:** Autenticación y gestión de usuarios.

**Componentes:**

```
auth/
├── presentation/
│   ├── LoginActivity
│   ├── RegisterFragment
│   └── LoginViewModel
├── domain/
│   ├── LoginUseCase
│   ├── RegisterUseCase
│   └── AuthRepository (interface)
└── data/
    ├── AuthRepositoryImpl
    └── AuthRemoteDataSource
```

**Casos de uso:**
- Iniciar sesión
- Registrarse
- Cerrar sesión
- Refresh token

---

## 📊 Dashboard Module

**Ubicación:** `:feature:dashboard`

**Responsabilidad:** Panel de control principal.

**Componentes:**
- DashboardActivity (pantalla principal)
- Widgets personalizables
- Sincronización de datos

---

## ⚙️ Settings Module

**Ubicación:** `:feature:settings`

**Responsabilidad:** Configuración de la app.

**Contenido:**
- Preferencias de usuario
- Tema (dark/light)
- Idioma
- Permisos

---

## 💾 Data Module

**Ubicación:** `:data`

**Responsabilidad:** Acceso a datos (API, BD, caché).

**Estructura:**
```
data/
├── database/
│   ├── dao/
│   │   ├── UserDao
│   │   └── SessionDao
│   ├── entity/
│   │   ├── UserEntity
│   │   └── SessionEntity
│   └── AppDatabase
├── remote/
│   ├── api/
│   │   ├── AuthApi
│   │   └── UserApi
│   ├── dto/
│   │   ├── LoginRequest
│   │   └── UserResponse
│   └── interceptor/
│       └── AuthInterceptor
└── repository/
    ├── AuthRepositoryImpl
    └── UserRepositoryImpl
```

**Tecnologías:**
- **Room**: SQLite local
- **Retrofit**: HTTP client
- **OkHttp**: HTTP interceptor

---

## 🔄 Comunicación entre módulos

### Desde auth → dashboard

```kotlin
// En AuthViewModel
private val _navigationEvent = MutableLiveData<AuthEvent>()

sealed class AuthEvent {
    object LoginSuccess : AuthEvent()
    object LogoutSuccess : AuthEvent()
}

// En AuthActivity (observa eventos)
authViewModel.navigationEvent.observe(this) { event ->
    when (event) {
        is AuthEvent.LoginSuccess -> {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}
```

### Inyección de dependencias (Hilt)

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource
    ): AuthRepository = AuthRepositoryImpl(remoteDataSource)
}
```

---

## ✅ Checklist para nuevo módulo

Si necesitas agregar un nuevo módulo:

- [ ] Crear carpeta en `feature/[nombre]`
- [ ] Crear `build.gradle.kts`
- [ ] Estructura presentation/domain/data
- [ ] Definir repository interface en domain
- [ ] Implementar en data
- [ ] Crear ViewModels en presentation
- [ ] Agregar Hilt module
- [ ] Incluir en `settings.gradle.kts`

---

**Siguiente:** [Base de datos](./database.md)
