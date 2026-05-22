---
sidebar_position: 10
---

# 🤝 Guía de contribución

¡Gracias por contribuir! Sigue estas directrices para mantener la calidad.

---

## Antes de empezar

- Haz un fork del repositorio
- Lee [CODE_OF_CONDUCT.md](https://github.com/39Luka/Proyecto_intermodular_movil_kotlin/blob/main/CODE_OF_CONDUCT.md)
- Abre un issue para discutir cambios grandes

---

## Pasos

### 1. Crear rama

```bash
# Siempre desde develop
git checkout develop
git pull origin develop

# Crear rama con patrón: type/descripcion
git checkout -b feature/nueva-funcionalidad
# o
git checkout -b fix/bug-critico
git checkout -b docs/actualizar-readme
```

### 2. Hacer cambios

- Sigue las [Convenciones de código](#convenciones)
- Escribe tests para tu código
- Actualiza documentación

### 3. Verificar cambios

```bash
# Lint
./gradlew lint

# Tests
./gradlew test
./gradlew connectedAndroidTest

# Build
./gradlew build
```

### 4. Commit

Usa [Conventional Commits](https://www.conventionalcommits.org/):

```bash
git commit -m "feat(auth): agregar autenticación con Google"
git commit -m "fix(ui): corregir crash en dashboard"
git commit -m "docs: actualizar guía de setup"
git commit -m "test: agregar tests para LoginViewModel"
```

### 5. Push y Pull Request

```bash
git push origin feature/nueva-funcionalidad
```

En GitHub:
- Click "Compare & pull request"
- Completa la descripción
- Espera revisión

---

## Convenciones de código

### Kotlin

```kotlin
// ✅ Bueno
class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(id: String) {
        viewModelScope.launch {
            _user.value = repository.getUser(id)
        }
    }
}

// ❌ Evitar
class UserViewModel : ViewModel() {
    var user = MutableLiveData<User>()  // ¡Mutable!
    
    fun loadUser(id: String) {  // Sin ViewModel scope
        GlobalScope.launch {
            user.postValue(getUser(id))
        }
    }
}
```

### Naming

```kotlin
// ✅
private val isUserLoggedIn: Boolean
fun calculateTotalPrice(): Double
val MAX_RETRY_COUNT = 3

// ❌
private val isLoggedIn_user: Boolean
fun calculate_total_price(): Double
val maxRetryCount = 3  // Debe ser const
```

### Imports

```kotlin
// ✅ Organizado
import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.proyecto.domain.User
import com.proyecto.data.repository.UserRepository

// ❌ Desordenado
import com.proyecto.domain.*
import androidx.lifecycle.*
```

---

## Testing

Agrega tests para:
- ViewModels
- Use Cases
- Repositories
- Componentes de UI críticos

```kotlin
@Test
fun testLoginSuccess() = runTest {
    // Arrange
    // Act
    // Assert
}
```

Mínimo 70% de cobertura en nuevas funcionalidades.

---

## Review checklist

Antes de pedir review, verifica:

- [ ] Código sigue convenciones
- [ ] Tests pasan (100%)
- [ ] Build sin errores
- [ ] Lint sin warnings
- [ ] Documentación actualizada
- [ ] Commits bien descriptivos

---

## Tipos de commits

```
feat:   Nueva funcionalidad
fix:    Corrección de bug
docs:   Cambios en documentación
style:  Formato, imports (sin lógica)
refactor: Reestructuración de código
test:   Agregar o actualizar tests
chore:  Cambios en config/dependencies
perf:   Mejora de performance
ci:     Cambios en CI/CD
```

---

## Comunidad

- 💬 Preguntas en [Discussions](https://github.com/39Luka/Proyecto_intermodular_movil_kotlin/discussions)
- 🐛 Bugs en [Issues](https://github.com/39Luka/Proyecto_intermodular_movil_kotlin/issues)
- 📧 Contacto: support@proyecto.com

---

**¡Gracias por contribuir!** 🎉
