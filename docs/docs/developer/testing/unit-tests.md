---
sidebar_position: 1
title: Pruebas Unitarias
---

# Testing en Android

Para garantizar la estabilidad de **La Croassantina**, hemos implementado una suite de pruebas unitarias que validan la lógica de los ViewModels y Repositorios.

## Herramientas utilizadas

- **JUnit 4**: El motor principal para ejecutar las pruebas.
- **MockK**: Librería nativa de Kotlin para simular el comportamiento de dependencias (Repositories, APIs).
- **Turbine**: Utilizada para testear flujos reactivos (`StateFlow` y `Flow`) de forma sencilla.
- **Coroutines Test**: Permite controlar el paso del tiempo en funciones suspendidas.

## Cómo ejecutar los tests

### Desde Android Studio (Recomendado)
1. Abre la pestaña **Project** a la izquierda.
2. Navega hasta `app/src/test/java/net/iesochoa/silvia/projecto_intermodular/`.
3. Haz clic derecho sobre la carpeta `viewmodel` o `data` y selecciona **Run 'Tests in...'**.

### Desde la Terminal
Ejecuta el siguiente comando en la raíz del proyecto:
```bash
./gradlew test
```

## Ejemplo de un Test Real
Los tests se centran en verificar que el estado de la interfaz (`UiState`) reaccione correctamente a las acciones del usuario o errores de red.

```kotlin
@Test
fun login_failure_sets_error_message() = runTest {
    // Simulamos un fallo en la API
    coEvery { authRepository.login(any(), any()) } throws Exception("Credenciales incorrectas")
    
    // Ejecutamos la acción en el ViewModel
    viewModel.login { }
    
    // Verificamos que el estado de la UI contiene el error
    assertEquals("Credenciales incorrectas", viewModel.uiState.value.errorMessage)
}
```
