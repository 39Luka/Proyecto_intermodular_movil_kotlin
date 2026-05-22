---
sidebar_position: 1
---

# ✅ Tests unitarios

## Setup

```kotlin
// build.gradle.kts
testImplementation("junit:junit:4.13.2")
testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
```

---

## Estructura de tests

```
src/
├── test/java/com/proyecto/
│   ├── viewmodel/
│   │   ├── LoginViewModelTest
│   │   └── DashboardViewModelTest
│   ├── domain/
│   │   ├── LoginUseCaseTest
│   │   └── UserRepositoryTest
│   └── data/
│       ├── database/
│       │   └── UserDaoTest
│       └── remote/
│           └── AuthApiTest
```

---

## Test de ViewModel

```kotlin
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val loginUseCase = mock<LoginUseCase>()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun testLoginSuccess() = runTest {
        // Arrange
        val email = "test@test.com"
        val password = "password123"
        val expectedUser = User("1", "Test User", email)
        
        coEvery { 
            loginUseCase(email, password) 
        } returns Result.success(expectedUser)

        // Act
        viewModel.login(email, password)

        // Assert
        assertEquals(LoginState.Success(expectedUser), viewModel.state.value)
    }

    @Test
    fun testLoginError() = runTest {
        val email = "test@test.com"
        val password = "wrong"
        
        coEvery { 
            loginUseCase(email, password) 
        } returns Result.failure(Exception("Invalid credentials"))

        viewModel.login(email, password)

        assert(viewModel.state.value is LoginState.Error)
    }
}
```

---

## Test de Repository

```kotlin
class UserRepositoryTest {
    private val userApi = mock<UserApi>()
    private val userDao = mock<UserDao>()
    private val tokenManager = mock<TokenManager>()

    private val repository = UserRepositoryImpl(userApi, userDao, tokenManager)

    @Test
    fun getUser_Success() = runTest {
        // Arrange
        val userId = "123"
        val mockResponse = UserResponse("123", "Juan", "juan@test.com", null, "", "")
        
        coEvery { tokenManager.getToken() } returns "token"
        coEvery { userApi.getUserById(userId, "token") } returns 
            Response.success(mockResponse)

        // Act
        val result = repository.getUser(userId)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(mockResponse.toDomain(), result.getOrNull())
        coVerify { userDao.insertUser(any()) }
    }
}
```

---

## Test de UseCase

```kotlin
class LoginUseCaseTest {
    private val userRepository = mock<UserRepository>()
    private val tokenManager = mock<TokenManager>()
    
    private val loginUseCase = LoginUseCase(userRepository, tokenManager)

    @Test
    fun invoke_ValidCredentials() = runTest {
        val email = "user@test.com"
        val password = "pass123"

        coEvery { 
            userRepository.login(email, password) 
        } returns Result.success(User("1", "User", email))

        val result = loginUseCase(email, password)

        assertTrue(result.isSuccess)
        coVerify { tokenManager.saveToken(any()) }
    }
}
```

---

## Ejecutar tests

```bash
# Todos los tests unitarios
./gradlew test

# Tests específicos
./gradlew test --tests "*.LoginViewModelTest"

# Con reporte
./gradlew test --tests "*" --scan
```

---

## Coverage de código

```bash
# Habilitar en build.gradle.kts
plugins {
    id("jacoco")
}

./gradlew testDebugUnitTest jacocoTestDebugUnitTestReport
```

---

**Siguiente:** [Integration Tests](./integration-tests.md)
