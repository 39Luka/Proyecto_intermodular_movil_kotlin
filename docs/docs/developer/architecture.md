---
sidebar_position: 5
---

# 🏗️ Arquitectura

## Visión general

El proyecto utiliza **arquitectura modular** con **MVVM** (Model-View-ViewModel).

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (Activities, Fragments, ViewModels)    │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Domain Layer                    │
│  (Use Cases, Repositories)              │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Data Layer                      │
│  (Database, API, Cache)                 │
└─────────────────────────────────────────┘
```

---

## Capas

### 1. **Presentation Layer** (Interfaz)
- **Activities**: Pantallas principales
- **Fragments**: Componentes reutilizables
- **ViewModels**: Lógica de presentación
- **Composables**: UI con Jetpack Compose (futuro)

### 2. **Domain Layer** (Lógica de negocio)
- **Use Cases**: Funcionalidades específicas
- **Repositories**: Abstracción de datos
- **Entities**: Modelos del dominio

### 3. **Data Layer** (Persistencia)
- **Database**: Room (SQLite)
- **API**: Retrofit (servicios web)
- **Cache**: Shared Preferences

---

## Patrones utilizados

### Dependency Injection (Hilt)
```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    // ...
}
```

### Repository Pattern
```kotlin
interface UserRepository {
    suspend fun getUser(id: String): Result<User>
    suspend fun saveUser(user: User): Result<Unit>
}
```

### MVVM
```
UI (Activity/Fragment)
    ↓
ViewModel (ReduceState)
    ↓
Repository (Data operations)
    ↓
Data Source (API/DB)
```

---

## Flow de datos

```
1. Usuario interactúa con UI
   ↓
2. ViewModel recibe evento
   ↓
3. ViewModel llama Use Case
   ↓
4. Use Case llama Repository
   ↓
5. Repository accede a Data Layer
   ↓
6. Datos retornan a ViewModel
   ↓
7. ViewModel emite state
   ↓
8. UI se actualiza
```

---

## Módulos principales

| Módulo | Responsabilidad |
|--------|-----------------|
| `:app` | Aplicación principal |
| `:core` | Utilidades compartidas |
| `:auth` | Autenticación |
| `:dashboard` | Panel de control |
| `:settings` | Configuración |
| `:data` | Acceso a datos |

---

**Siguiente:** [Estructura del proyecto](./project-structure.md)
