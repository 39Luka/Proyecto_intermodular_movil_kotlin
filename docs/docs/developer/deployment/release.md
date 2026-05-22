---
sidebar_position: 2
---

# 🚀 Release

## Checklist pre-release

- [ ] Incrementar versionCode y versionName
- [ ] Actualizar CHANGELOG.md
- [ ] Ejecutar todos los tests
- [ ] Verificar lint sin errores
- [ ] Generar APK release
- [ ] Probar APK en dispositivo real
- [ ] Crear tag Git

---

## Versionamiento

### Semantic Versioning

Formato: `MAJOR.MINOR.PATCH-PRERELEASE+BUILD`

Ejemplos:
- `1.0.0` - Release inicial
- `1.1.0` - Nueva característica (minor)
- `1.0.1` - Bug fix (patch)
- `2.0.0` - Breaking changes (major)

### Actualizar versión

```gradle
// build.gradle.kts
defaultConfig {
    versionCode = 2      // Incrementa por cada release
    versionName = "1.1.0"
}
```

---

## Build Release APK

```bash
# Build APK
./gradlew assembleRelease

# Build AAB (Google Play)
./gradlew bundleRelease

# Output
# APK: app/build/outputs/apk/release/app-release.apk
# AAB: app/build/outputs/bundle/release/app-release.aab
```

---

## Google Play Store

### Requisitos
- Cuenta desarrollador Google Play
- Firma digital (keystore)
- Screenshots y descripción
- Política de privacidad

### Pasos

1. **Crear aplicación en Play Console**
   - Nombre
   - Descripción
   - Categoría

2. **Subir AAB**
   - Google Play aprovecha AAB para optimizar descargas
   - Más pequeño que APK

3. **Configurar listado**
   - Screenshots (4-8)
   - Descripción breve
   - Cambios en esta versión

4. **Revisar contenido**
   - Clasificación
   - Privacidad
   - Permisos

5. **Enviar revisión**
   - Esperar 2-4 horas para revisión manual
   - Google valida que cumpla con políticas

---

## Deployment automático (GitHub Actions)

Ejemplo workflow:

```yaml
name: Build and Release

on:
  push:
    tags:
      - 'v*'

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK
      uses: actions/setup-java@v3
      with:
        java-version: '11'
    
    - name: Build Release
      run: ./gradlew assembleRelease bundleRelease
    
    - name: Upload to Play Store
      uses: r0adkll/upload-google-play@v1
      with:
        serviceAccountJsonPlainText: \${{ secrets.PLAY_STORE_CREDENTIALS }}
        packageName: com.proyecto.app
        releaseFiles: app/build/outputs/bundle/release/app-release.aab
        track: internal
        inAppUpdatePriority: 5
```

---

## GitHub Release

```bash
# Crear tag
git tag v1.1.0
git push origin v1.1.0

# Crear release con notas
# En GitHub → Releases → Draft new release
```

---

## Post-release

1. ✅ Monitorear crashes en Play Store
2. ✅ Responder a reviews
3. ✅ Preparar siguiente versión
4. ✅ Documentar cambios

---

## Rollback

Si hay problemas críticos:

```bash
# Regresar a versión anterior
git revert <commit>
git tag v1.1.1
```

En Play Store → Administrar lanzamientos → Detener lanzamiento

---

**Siguiente:** [Contribuir](../contributing.md)
