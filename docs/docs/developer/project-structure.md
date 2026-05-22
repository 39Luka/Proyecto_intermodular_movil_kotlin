---
sidebar_position: 6
---

# Estructura del proyecto

El proyecto sigue una estructura de módulo único (`:app`) organizada por paquetes funcionales y técnicos.

```
Proyecto_intermodular_movil_kotlin/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/net/iesochoa/silvia/projecto_intermodular/
│   │   │   │   ├── data/        # API (Retrofit), Repositorios, Models
│   │   │   │   ├── model/       # UI States (clases para la interfaz)
│   │   │   │   ├── ui/          # Capa de Interfaz
│   │   │   │   │   ├── components/ # Widgets reutilizables
│   │   │   │   │   ├── navigation/ # AppNavigation y Screens
│   │   │   │   │   ├── screens/    # Pantallas completas
│   │   │   │   │   └── theme/      # Colores, Tipografía (Compose)
│   │   │   │   ├── viewmodel/   # ViewModels y AppModule (Hilt)
│   │   │   │   └── utils/       # Validadores, Mappers, Extensiones
│   │   │   └── res/
│   │   │       ├── drawable/    # Recursos gráficos e iconos
│   │   │       └── values/      # Strings y configuración
│   │   ├── test/                # Unit Tests (JUnit, MockK, Turbine)
│   │   └── androidTest/         # UI Tests (Compose Rule)
│   └── build.gradle.kts
├── docs/                        # Documentación Docusaurus
├── build.gradle.kts             # Configuración raíz
└── settings.gradle.kts          # Definición de módulos
```

---

## Convenciones de nombres

### Packages
Se utiliza la ruta base: `net.iesochoa.silvia.projecto_intermodular` seguida de la capa técnica.

### Archivos Kotlin
- **Screens**: `[Nombre]Screen.kt` (Contiene el Composable principal).
- **ViewModels**: `[Nombre]ViewModel.kt`.
- **Repositorios**: `[Entidad]Repository.kt`.
- **UI States**: `[Nombre]UiState.kt`.

### Recursos
- **Strings**: `strings.xml` (Localizados para español).
- **Iconos**: Se utilizan principalmente `MaterialIcons` extendidos.

---

## Dependencias principales

En `app/build.gradle.kts`:

```kotlin
// UI (Jetpack Compose)
implementation(libs.androidx.compose.ui)
implementation(libs.androidx.compose.material3)

// Inyección de Dependencias (Hilt)
implementation("com.google.dagger:hilt-android:2.51.1")
ksp("com.google.dagger:hilt-compiler:2.51.1")

// Red y Serialización
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Persistencia
implementation("androidx.datastore:datastore-preferences:1.0.0")

// Gestión de Imágenes
implementation("io.coil-kt:coil-compose:2.5.0")

// Concurrencia
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

---

**Siguiente:** [Testing](./testing/unit-tests.md)
