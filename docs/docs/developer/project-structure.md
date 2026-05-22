---
sidebar_position: 6
---

# 📂 Estructura del proyecto

```
Proyecto_intermodular_movil_kotlin/
├── app/                          # Módulo principal
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/proyecto/
│   │   │   │   ├── ui/          # Activities, Fragments
│   │   │   │   ├── viewmodel/   # ViewModels
│   │   │   │   ├── di/          # Inyección de dependencias
│   │   │   │   └── App.kt       # Clase aplicación
│   │   │   └── res/
│   │   │       ├── layout/      # Layouts XML
│   │   │       ├── drawable/    # Recursos gráficos
│   │   │       ├── values/      # Strings, colores
│   │   │       └── menu/        # Menus
│   │   ├── test/                # Tests unitarios
│   │   └── androidTest/         # Tests instrumentados
│   └── build.gradle.kts
│
├── core/                         # Módulo compartido
│   ├── src/main/java/
│   │   ├── utils/               # Utilidades
│   │   ├── extension/           # Extensiones Kotlin
│   │   ├── constants/           # Constantes
│   │   └── base/                # Clases base
│   └── build.gradle.kts
│
├── feature/
│   ├── auth/                    # Feature: Autenticación
│   │   ├── src/main/java/
│   │   │   ├── presentation/
│   │   │   ├── domain/
│   │   │   └── data/
│   │   └── build.gradle.kts
│   │
│   ├── dashboard/               # Feature: Dashboard
│   │   └── ... (misma estructura)
│   │
│   └── settings/                # Feature: Configuración
│       └── ... (misma estructura)
│
├── data/                        # Módulo de datos
│   ├── src/main/java/
│   │   ├── database/
│   │   │   ├── dao/             # Data Access Objects
│   │   │   ├── entity/          # Entidades de BD
│   │   │   └── AppDatabase.kt   # Configuración Room
│   │   ├── remote/
│   │   │   ├── api/             # Interfaces Retrofit
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   └── ApiClient.kt
│   │   └── repository/          # Implementaciones Repository
│   └── build.gradle.kts
│
├── build.gradle.kts             # Configuración principal
├── settings.gradle.kts          # Módulos incluidos
├── local.properties             # Local (no comitir)
└── gradle.properties            # Propiedades Gradle

```

---

## Convenciones de nombres

### Packages
```
com.proyecto.[feature/core].[layer]
com.proyecto.auth.presentation    ✅
com.proyecto.auth.domain          ✅
com.proyecto.auth.data            ✅
```

### Archivos Kotlin
```
- Activities: [NombrePantalla]Activity.kt
- Fragments: [NombreComponente]Fragment.kt
- ViewModels: [Nombre]ViewModel.kt
- UseCase: [Acción][Nombre]UseCase.kt
```

### Layouts XML
```
- Activities: activity_[nombre].xml
- Fragments: fragment_[nombre].xml
- Items: item_[nombre].xml
```

### Recursos
```
- Strings: strings.xml
- Colores: colors.xml
- Estilos: styles.xml
- Dimensiones: dimens.xml
```

---

## Dependencias principales

En `build.gradle.kts`:

```kotlin
// Android
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("androidx.core:core-ktx:1.10.1")

// Hilt DI
implementation("com.google.dagger:hilt-android:2.46")
kapt("com.google.dagger:hilt-compiler:2.46")

// Room Database
implementation("androidx.room:room-runtime:2.5.2")
kapt("androidx.room:room-compiler:2.5.2")

// Retrofit + OkHttp
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.11.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
```

---

**Siguiente:** [Módulos](./modules.md)
