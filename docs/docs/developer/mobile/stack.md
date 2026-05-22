---
sidebar_position: 2
title: Stack Tecnológico Móvil
---

# Tecnologías Utilizadas en Android

Para garantizar un rendimiento nativo y mantenibilidad, se han usado las librerías oficiales de Google:

| Capa | Tecnología |
| :--- | :--- |
| **Lenguaje** | Kotlin 2.0 |
| **Interfaz** | Jetpack Compose (Declarativa) |
| **Inyección de Dep.** | Hilt (Dagger) |
| **Red** | Retrofit 2 + OkHttp |
| **Persistencia** | Jetpack DataStore (Preferences) |
| **Imágenes** | Coil-Compose |
| **Asincronía** | Coroutines + Flow |

## Comunicación con el Backend
Se ha implementado un **AuthInterceptor** en el módulo de red que intercepta cada petición saliente para inyectar automáticamente el token JWT:

```kotlin
if (!token.isNullOrEmpty()) {
    requestBuilder.addHeader("Authorization", "Bearer $token")
}
```
