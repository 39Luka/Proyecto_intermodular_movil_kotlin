---
sidebar_position: 1
---

# 🔨 Build

## Tipos de build

### Debug
Para desarrollo y testing:
```bash
./gradlew assembleDebug
```

APK generado: `app/build/outputs/apk/debug/app-debug.apk`

### Release
Para producción:
```bash
./gradlew assembleRelease
```

APK generado: `app/build/outputs/apk/release/app-release.apk`

---

## Configuración de build

### build.gradle.kts (app)

```kotlin
android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.proyecto.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}
```

---

## Configurar firma (Release)

### Crear keystore

```bash
keytool -genkey -v -keystore my-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias my-key-alias
```

### Configurar en gradle

Crear `keystore.properties`:
```
storeFile=my-release-key.jks
storePassword=mypassword
keyAlias=my-key-alias
keyPassword=mypassword
```

En `build.gradle.kts`:
```kotlin
def keystoreProperties = new Properties()
keystoreProperties.load(new FileInputStream(rootProject.file('keystore.properties')))

android {
    signingConfigs {
        release {
            keyAlias keystoreProperties['keyAlias']
            keyPassword keystoreProperties['keyPassword']
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
        }
    }
}
```

---

## Build con variable de entorno

```gradle
buildTypes {
    debug {
        buildConfigField("String", "API_BASE_URL", "\"https://dev-api.com\"")
    }
    release {
        buildConfigField("String", "API_BASE_URL", "\"https://api.com\"")
    }
}
```

Usar en código:
```kotlin
val apiUrl = BuildConfig.API_BASE_URL
```

---

## Troubleshooting

### "Build failed"
```bash
./gradlew clean
./gradlew build -x lintVitalRelease
```

### Gradle cache issue
```bash
./gradlew build --refresh-dependencies
```

### Memory error
```bash
export GRADLE_OPTS="-Xmx2048m"
./gradlew build
```

---

**Siguiente:** [Release](./release.md)
