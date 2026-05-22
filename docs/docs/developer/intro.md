---
sidebar_position: 1
---

# Manual de Desarrollador

Bienvenido a la documentación técnica de **La Croassantina Móvil**.

## Contenido

- **[Setup](./setup.md)** - Configura tu entorno de desarrollo
- **[Arquitectura](./architecture.md)** - MVVM y Clean Architecture
- **[Estructura del proyecto](./project-structure.md)** - Organización de paquetes
- **[App Móvil](./mobile/arquitectura.md)** - Detalles específicos de Android
- **[Testing](./testing/unit-tests.md)** - Pruebas unitarias con MockK
- **[Recursos](./resources.md)** - Librerías y enlaces útiles

## Inicio rápido

```bash
# 1. Clonar repositorio
git clone https://github.com/39Luka/Proyecto_intermodular_movil_kotlin.git
cd Proyecto_intermodular_movil_kotlin

# 2. Sincronizar Gradle en Android Studio
# (Abre la carpeta raíz y espera a que el IDE termine la indexación)

# 3. Ejecutar en emulador o dispositivo
# Pulsa el botón "Run" (flecha verde) en Android Studio
```

## Stack técnico real

- **Lenguaje:** Kotlin 2.0
- **UI:** Jetpack Compose (Declarativo)
- **Networking:** Retrofit 2 + OkHttp
- **DI:** Hilt (Dagger)
- **Persistencia:** Jetpack DataStore (Preferences)
- **Imágenes:** Coil-Compose
- **Async:** Coroutines + Flow

---

**¿Primer contacto?** Empieza con [Setup](./setup.md)
