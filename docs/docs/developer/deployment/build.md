---
sidebar_position: 1
title: Construcción y APK
---

# Generación de la App (Build)

El proceso de construcción transforma el código fuente de Kotlin en un archivo ejecutable para Android (**APK**).

## Generar el archivo APK

Si necesitas compartir la aplicación o instalarla manualmente en un dispositivo físico, sigue estos pasos:

### 1. Desde Android Studio
1. Ve al menú superior **Build**.
2. Selecciona **Build Bundle(s) / APK(s)**.
3. Haz clic en **Build APK(s)**.
4. Cuando termine, aparecerá un globo informativo abajo a la derecha. Pulsa en **locate** para abrir la carpeta con el archivo `app-debug.apk`.

### 2. Desde la Terminal
Ejecuta el siguiente comando para limpiar y generar el ejecutable:
```bash
./gradlew assembleDebug
```
El archivo se generará en:
`app/build/outputs/apk/debug/app-debug.apk`

---

## Ejecución Directa

Para probar la app durante el desarrollo:
1. Conecta un móvil con **Depuración USB** activada o inicia un **Emulador** (API 26+).
2. Pulsa el botón **Run** (flecha verde) en la barra superior.
3. Android Studio compilará e instalará la app automáticamente en el dispositivo seleccionado.
