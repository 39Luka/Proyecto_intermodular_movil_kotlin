package net.iesochoa.silvia.projecto_intermodular

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.iesochoa.silvia.projecto_intermodular.ui.components.BottomBar
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.AppNavigation
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.Screen
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Projecto_IntermodularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projecto_IntermodularTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                        BottomBar(navController)
                        }
                    }

                ) { innerPadding ->

                    AppNavigation(navController = navController,
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

