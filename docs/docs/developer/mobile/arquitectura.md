---
sidebar_position: 1
title: Arquitectura Móvil
---

# Arquitectura del Proyecto Android

La aplicación móvil sigue los principios de **Clean Architecture** y el patrón de diseño **MVVM (Model-View-ViewModel)**.

## Estructura de Carpetas
```text
net.iesochoa.silvia.projecto_intermodular/
├── data/           # Repositorios, API Service (Retrofit) y DataStore.
├── model/          # UI States y modelos de datos de dominio.
├── ui/
│   ├── components/ # Componentes Compose reutilizables (Botones, Cards).
│   ├── screens/    # Pantallas completas de la aplicación.
│   ├── theme/      # Sistema de diseño (Colores, Tipografía).
│   └── navigation/ # Grafo de navegación y rutas.
├── viewmodel/      # Lógica de negocio e integración con Hilt.
└── utils/          # Mappers y validadores.
```

## Patrones Utilizados
- **Single Source of Truth:** Los repositorios en la carpeta `data` son la única fuente de datos para los ViewModels.
- **Unidirectional Data Flow (UDF):** El ViewModel expone un único `StateFlow` que la UI observa. La UI envía eventos al ViewModel.
