---
sidebar_position: 4
---

# 🚀 Getting Started para desarrolladores

Guía rápida para empezar a desarrollar en 5 minutos.

---

## 1. Clonar y configurar

```bash
# Clonar
git clone https://github.com/39Luka/Proyecto_intermodular_movil_kotlin.git
cd Proyecto_intermodular_movil_kotlin

# Ver ramas disponibles
git branch -a

# Crear rama de desarrollo
git checkout -b feature/mi-feature
```

---

## 2. Abrir en Android Studio

1. Abre Android Studio
2. File → Open → Selecciona la carpeta
3. Espera a que sincronice
4. ¡Listo!

---

## 3. Ejecutar app

```bash
# Debug
./gradlew installDebug

# O en Android Studio: Run → Run 'app'
```

---

## 4. Crear primera feature

Estructura básica:

```kotlin
// 1. Crear interface en domain layer
interface MyRepository {
    suspend fun doSomething(): Result<String>
}

// 2. Crear implementación en data layer
@Inject
class MyRepositoryImpl(
    private val api: MyApi
) : MyRepository {
    override suspend fun doSomething() = runCatching {
        api.getSomething().body ?: throw Exception("Empty")
    }
}

// 3. Crear UseCase en domain
@Inject
class MyUseCase(private val repo: MyRepository) {
    suspend operator fun invoke() = repo.doSomething()
}

// 4. Crear ViewModel
@HiltViewModel
class MyViewModel @Inject constructor(
    private val useCase: MyUseCase
) : ViewModel() {
    // Logic
}

// 5. Crear UI (Activity/Fragment)
class MyActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    // UI code
}
```

---

## 5. Testing

```bash
# Tests unitarios
./gradlew test

# Tests instrumentados
./gradlew connectedAndroidTest
```

---

## 6. Hacer commit

```bash
git add .
git commit -m "feat: descripción concisa de cambios"
git push origin feature/mi-feature
```

---

## Próximos pasos

- 📖 Lee [Arquitectura](./architecture.md)
- 📂 Entiende la [Estructura](./project-structure.md)
- 🧪 Escribe tests en [Testing](./testing)
- 🚀 Deploy en [Release](./deployment/release.md)

---

**¿Problemas?** Consulta [Solución de problemas](../troubleshooting.md)
