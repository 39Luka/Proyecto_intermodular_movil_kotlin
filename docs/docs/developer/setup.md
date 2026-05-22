---
sidebar_position: 2
---

# 🛠️ Setup - Configuración del entorno

## Requisitos previos

- **JDK 11+** - Java Development Kit
- **Android Studio** - IDE principal
- **SDK de Android 8.0+** (API 26)
- **Gradle 7.5+**
- **Git**

## Instalación paso a paso

### 1. Instalar Java (JDK)

**Windows:**
```bash
# Usando scoop
scoop install openjdk11

# O descargar de https://www.oracle.com/java/technologies/downloads/
```

**Mac:**
```bash
brew install openjdk@11
```

**Linux:**
```bash
sudo apt-get install openjdk-11-jdk
```

Verificar: `java -version`

---

### 2. Instalar Android Studio

1. Descarga desde [developer.android.com](https://developer.android.com/studio)
2. Ejecuta el instalador
3. Completa la instalación
4. Abre Android Studio

---

### 3. Configurar SDK

En Android Studio:
1. **Tools → SDK Manager**
2. **SDK Platforms** → Instala API 33+ (recomendado API 34)
3. **SDK Tools** → Instala:
   - Android SDK Build-Tools
   - Android Emulator
   - Android SDK Platform-Tools

---

### 4. Clonar el repositorio

```bash
git clone https://github.com/39Luka/Proyecto_intermodular_movil_kotlin.git
cd Proyecto_intermodular_movil_kotlin
```

---

### 5. Abrir en Android Studio

1. Abre Android Studio
2. **File → Open → Selecciona la carpeta del proyecto**
3. Espera a que se sincronice Gradle
4. ✅ Listo!

---

## Emulador

### Crear emulador

1. **Tools → Device Manager**
2. **Create Virtual Device**
3. Selecciona dispositivo (Pixel 6)
4. Selecciona API (34 recomendado)
5. Finaliza la configuración

### Ejecutar emulador

```bash
# Listar emuladores
emulator -list-avds

# Iniciar emulador
emulator -avd <nombre_emulador>
```

---

## Build y ejecución

```bash
# Compilar proyecto
./gradlew build

# Instalar en emulador/dispositivo
./gradlew installDebug

# Ejecutar app
./gradlew runDebug
```

---

## Solucionar problemas

### Gradle no funciona
```bash
./gradlew clean build
```

### Sincronización de Gradle lenta
1. **File → Settings → Build, Execution, Deployment → Gradle**
2. Activa **"Offline work"** (si tienes dependencias en caché)

### No se detecta dispositivo
```bash
adb devices
adb reboot
```

---

✅ **¡Ya estás listo para desarrollar!**

Siguiente: [Arquitectura del proyecto](./architecture.md)
