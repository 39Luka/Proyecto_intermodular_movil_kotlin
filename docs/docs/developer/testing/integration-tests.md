---
sidebar_position: 2
---

# 🧪 Tests de integración

## Setup

```kotlin
// build.gradle.kts
androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.test:runner:1.5.2")
androidTestImplementation("androidx.test:rules:1.5.0")
```

---

## Estructura

```
src/androidTest/java/com/proyecto/
├── ui/
│   ├── LoginActivityTest
│   └── DashboardActivityTest
├── database/
│   └── AppDatabaseTest
└── api/
    └── ApiIntegrationTest
```

---

## Test de Activity

```kotlin
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginButtonClick() {
        onView(withId(R.id.emailInput))
            .perform(typeText("test@test.com"))
        
        onView(withId(R.id.passwordInput))
            .perform(typeText("password123"))

        onView(withId(R.id.loginButton))
            .perform(click())

        onView(withText("Login exitoso"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyEmailError() {
        onView(withId(R.id.passwordInput))
            .perform(typeText("password123"))

        onView(withId(R.id.loginButton))
            .perform(click())

        onView(withId(R.id.emailError))
            .check(matches(isDisplayed()))
    }
}
```

---

## Test de Fragment

```kotlin
@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {
    @get:Rule
    val fragmentRule = FragmentScenarioRule(DashboardFragment::class.java)

    @Test
    fun testFragmentLaunch() {
        onView(withId(R.id.dashboardContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewItems() {
        onView(withId(R.id.itemsList))
            .check(matches(isDisplayed()))

        onView(allOf(
            isDescendantOfA(withId(R.id.itemsList)),
            withId(R.id.itemTitle)
        )).check(matches(hasMinimumChildCount(1)))
    }
}
```

---

## Test de API

```kotlin
@RunWith(AndroidJUnit4::class)
class AuthApiTest {
    private lateinit var retrofit: Retrofit
    private lateinit var authApi: AuthApi

    @Before
    fun setup() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.test.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        authApi = retrofit.create(AuthApi::class.java)
    }

    @Test
    fun testLoginSuccess() = runTest {
        val request = LoginRequest("user@test.com", "password")
        
        val response = authApi.login(request)

        assertTrue(response.isSuccessful)
        assertNotNull(response.body()?.token)
    }
}
```

---

## Test de BD (Instrumentado)

```kotlin
@RunWith(AndroidJUnit4::class)
class UserDaoInstrumentedTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveUser() = runTest {
        val user = UserEntity(
            "1", "Test User", "test@test.com", 0, 0
        )
        
        userDao.insertUser(user)
        val retrieved = userDao.getUserById("1")

        assertEquals(user, retrieved)
    }
}
```

---

## Ejecutar tests instrumentados

```bash
# Conectar dispositivo o emulador
adb devices

# Ejecutar todos los tests
./gradlew connectedAndroidTest

# Test específico
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.proyecto.ui.LoginActivityTest

# Reporte
./gradlew connectedAndroidTest --continue
```

---

## Espresso helpers

```kotlin
// Verificar si vista es visible
onView(withId(R.id.myView))
    .check(matches(isDisplayed()))

// Escribir texto
onView(withId(R.id.input))
    .perform(typeText("hello"))

// Click
onView(withId(R.id.button))
    .perform(click())

// Scroll
onView(withId(R.id.recyclerView))
    .perform(RecyclerViewActions.scrollToPosition<MyAdapter.MyViewHolder>(10))

// Verificar texto
onView(withId(R.id.textView))
    .check(matches(withText("Expected text")))
```

---

**Siguiente:** [Build & Release](../deployment/build.md)
