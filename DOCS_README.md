# 📚 Documentación - Proyecto Intermodular Móvil

Bienvenido a la documentación oficial del proyecto Kotlin para Android.

## 🚀 Iniciar documentación

La documentación está basada en **Docusaurus** y se sirve localmente.

### Requisitos
- Node.js 16+
- npm

### Pasos

```bash
# Navega a la carpeta docs
cd docs

# Instala dependencias (primera vez)
npm install

# Inicia servidor de desarrollo
npm start
```

La documentación estará disponible en: **http://localhost:3001**

(Si el puerto 3000 está ocupado, Docusaurus usará el 3001)

---

## 📖 Estructura de documentación

### 👤 Manual de Usuario (`docs/docs/user/`)
- **Introducción** - Qué es la app
- **Primeros pasos** - Instalación y setup
- **Características** - Guías de cada módulo
  - Login y autenticación
  - Dashboard
  - Configuración
- **Solución de problemas** - Errores comunes
- **FAQ** - Preguntas frecuentes

### 👨‍💻 Manual de Desarrollador (`docs/docs/developer/`)
- **Setup** - Configurar entorno de desarrollo
- **Getting Started** - Primeros pasos para programar
- **Arquitectura** - Visión general del proyecto
- **Estructura del proyecto** - Organización de carpetas
- **Módulos** - Descripción de cada módulo
- **Base de datos** - Room y SQLite
- **Testing** - Unit tests e integration tests
- **Deployment** - Build y release
- **Contributing** - Guía de contribución
- **Recursos** - Enlaces útiles

---

## 📝 Agregar/Editar documentación

### Crear nueva página

1. Crea archivo markdown en la carpeta correspondiente:
   ```
   docs/docs/user/mi-pagina.md
   o
   docs/docs/developer/mi-pagina.md
   ```

2. Agrega header con metadatos:
   ```markdown
   ---
   sidebar_position: 3
   ---

   # Mi Página
   Contenido aquí...
   ```

3. Actualiza `sidebars.ts` si es necesario

### Editar página existente

1. Edita el archivo `.md` directamente
2. Los cambios se reflejan automáticamente (hot reload)

### Agregar screenshots/imágenes

1. Guarda imágenes en: `docs/static/img/screenshots/`
2. Referencia en markdown:
   ```markdown
   ![Pantalla login](../../../static/img/screenshots/login.png)
   ```

---

## 🏗️ Build para producción

```bash
cd docs

# Generar sitio estático
npm run build

# Vista previa del build
npm run serve
```

Salida estática en: `docs/build/`

---

## 🚀 Deploy (GitHub Pages)

El proyecto está configurado para GitHub Pages.

### Configurar `docusaurus.config.ts`

```typescript
url: 'https://39luka.github.io',
baseUrl: '/Proyecto_intermodular_movil_kotlin/',
organizationName: '39Luka',
projectName: 'Proyecto_intermodular_movil_kotlin',
```

### Deploy automático

```bash
# Usar npm script (requiere GitHub token)
npm run deploy
```

O manualmente:
```bash
# Build
npm run build

# Push a gh-pages branch
git subtree push --prefix docs/build origin gh-pages
```

---

## 📸 Capturar screenshots de Android

### Desde emulador:

```bash
# Listar emuladores
emulator -list-avds

# Iniciar emulador
emulator -avd <nombre>

# Capturar pantalla
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png ./docs/static/img/screenshots/

# Ver en Android Studio: Tools → Device Manager → Screenshots
```

### Desde dispositivo real:

1. Conectar por USB
2. Ejecutar mismo comando `adb`

---

## 🎨 Personalización

### Tema de colores

Edita: `docs/src/css/custom.css`

### Navbar y footer

Edita: `docs/docusaurus.config.ts`

### Configuración general

Todo en: `docs/docusaurus.config.ts`

---

## 🔍 Troubleshooting

### Puerto 3000 ocupado
```bash
npm start
# Cuando pregunte, presiona Y para usar otro puerto
```

### Caché de npm
```bash
npm cache clean --force
npm install
npm start
```

### Error de build
```bash
npm run clean
npm run build
```

---

## 📚 Recursos

- [Docusaurus Docs](https://docusaurus.io/)
- [Markdown Syntax](https://docusaurus.io/docs/markdown-features)
- [MDX en Docusaurus](https://docusaurus.io/docs/markdown-features/mdx)

---

## ✅ Checklist para agregar feature a docs

- [ ] Crear archivo markdown en carpeta correspondiente
- [ ] Agregar header con `sidebar_position`
- [ ] Actualizar `sidebars.ts` si es nueva categoría
- [ ] Revisar en `npm start` (http://localhost:3001)
- [ ] Agregar links internos correctamente
- [ ] Agregar screenshots si es necesario
- [ ] Commit cambios
- [ ] Deploy si está todo listo

---

¿Preguntas? Consulta [Recursos](./docs/docs/developer/resources.md)
