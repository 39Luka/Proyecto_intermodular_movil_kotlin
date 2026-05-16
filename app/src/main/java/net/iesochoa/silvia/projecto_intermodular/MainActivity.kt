package net.iesochoa.silvia.projecto_intermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.iesochoa.silvia.projecto_intermodular.ui.components.BottomBar
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.AppNavigation
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.Screen
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme

/**
 * Actividad principal de la aplicación que sirve como punto de entrada.
 * Configura el tema, activa el modo edge-to-edge e inicializa la pantalla principal.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projecto_IntermodularTheme {
                MainScreen()
            }
        }
    }
}

/**
 * Composable principal que gestiona la estructura de la aplicación.
 * Controla la navegación y la visibilidad de la barra inferior (BottomBar).
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define en qué rutas se debe mostrar la barra de navegación inferior
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Catalog.route,
        Screen.Offers.route,
        Screen.Purchases.route,
        Screen.Cart.route
    )
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
