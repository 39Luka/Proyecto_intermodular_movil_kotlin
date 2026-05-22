---
sidebar_position: 4
---

# 🚀 Primeros pasos para desarrolladores

Guía rápida para empezar a desarrollar en La Croassantina.

---

## 1. Clonar y configurar

```bash
# Clonar repositorio
git clone https://github.com/39Luka/Proyecto_intermodular_movil_kotlin.git
cd Proyecto_intermodular_movil_kotlin

# Cambiar a rama de trabajo
git checkout -b feature/nueva-funcionalidad
```

---

## 2. Abrir en Android Studio

1. Abre **Android Studio Ladybug** (o superior).
2. **File → Open** → Selecciona la carpeta raíz del proyecto.
3. Espera a que la sincronización de Gradle finalice.
4. ✅ Si ves el botón de "Run" habilitado, todo está correcto.

---

## 3. Ejecutar la App

- **Físico:** Conecta tu móvil Android con depuración USB activada.
- **Emulador:** Crea un dispositivo virtual con API 34.
- **Acción:** Pulsa el botón **Run** (flecha verde) en la barra superior del IDE.

---

## 4. Estructura de una funcionalidad (Compose)

El flujo estándar para añadir una pantalla es:

```kotlin
// 1. Definir el estado en ui/model/
data class MyUiState(val data: String = "", val isLoading: Boolean = false)

// 2. Crear Repositorio en data/ (si es necesario nueva API)
class MyRepository(private val apiService: ApiService) { ... }

// 3. Crear ViewModel en viewmodel/
@HiltViewModel
class MyViewModel @Inject constructor(private val repository: MyRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState = _uiState.asStateFlow()
}

// 4. Crear Pantalla en ui/screens/
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val state by viewModel.uiState.collectAsState()
    // UI declarativa con Compose
}
```

---

## 5. Ejecutar Pruebas

Para asegurar que no has roto nada:

```bash
# Ejecutar Unit Tests desde terminal
./gradlew test
```

---

## Próximos pasos

- 📖 Lee la [Arquitectura](./architecture.md).
- 📂 Entiende la [Estructura de paquetes](./project-structure.md).
- 🧪 Aprende sobre el [Testing en Móvil](./mobile/testing).

---

