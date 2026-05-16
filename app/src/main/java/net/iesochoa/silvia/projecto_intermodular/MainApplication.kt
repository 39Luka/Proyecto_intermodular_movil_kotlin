package net.iesochoa.silvia.projecto_intermodular

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase de Aplicación base que inicializa Hilt para la inyección de dependencias.
 */
@HiltAndroidApp
class MainApplication : Application()
