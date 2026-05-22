---
sidebar_position: 5
---

# Arquitectura

## Visión general

El proyecto utiliza una arquitectura **MVVM (Model-View-ViewModel)** moderna con **Clean Architecture** mediante una capa de Repositorios.

```
┌─────────────────────────────────────────┐
│         Capa de Presentación            │
│       (Composables, ViewModels)         │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Capa de Dominio/Datos           │
│             (Repositories)              │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Capa de Infraestructura         │
│         (API Retrofit, DataStore)       │
└─────────────────────────────────────────┘
```

---

## Capas

### 1. **Capa de Presentación** (Interfaz)
- **Jetpack Compose**: UI 100% declarativa sin XML.
- **ViewModels**: Gestionan el estado de la UI mediante `StateFlow` y procesan los eventos del usuario.
- **UI States**: Clases de datos que encapsulan todo lo que la vista necesita mostrar.

### 2. **Capa de Datos** (Repositorios)
- **Repositories**: Actúan como la única fuente de verdad. Centralizan el acceso a los datos (API y persistencia local).
- **Mappers**: Transforman los objetos de la API (DTOs) en objetos optimizados para la UI.

### 3. **Capa de Infraestructura** (Fuentes Externas)
- **API (Retrofit)**: Comunicación con el backend en la nube.
- **DataStore**: Persistencia local asíncrona para tokens JWT y perfil de usuario.

---

## Patrones utilizados

### Inyección de Dependencias (Hilt)
Se utiliza Hilt para gestionar el ciclo de vida de los componentes y facilitar la testabilidad.

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    // ...
}
```

### Unidirectional Data Flow (UDF)
La interfaz solo reacciona a cambios en el estado expuesto por el ViewModel.

```
UI (Compose)
    ↑ (Observa StateFlow)
ViewModel (Lógica de negocio)
    ↓ (Llamadas suspendidas)
Repository (Gestión de datos)
```

---

## Flujo de datos

1. El usuario pulsa un botón en un **Composable**.
2. El **ViewModel** recibe el evento y lanza una **Corrutina**.
3. El ViewModel solicita datos al **Repositorio**.
4. El Repositorio obtiene los datos de la **API** mediante Retrofit.
5. El Repositorio mapea los datos y los devuelve al ViewModel.
6. El ViewModel actualiza el **StateFlow** con el nuevo estado.
7. La **UI** se repinta automáticamente con la nueva información.

---

**Siguiente:** [Estructura del proyecto](./project-structure.md)
