package net.iesochoa.silvia.projecto_intermodular.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.iesochoa.silvia.projecto_intermodular.ui.screens.LoginScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.RegisterScreen
import net.iesochoa.silvia.projecto_intermodular.viewmodel.LoginViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ){
        composable(Screen.Login.route){
            LoginScreen(
                onLoginSuccess = {navController.navigate(Screen.Home.route)},
                onRegisterClick = {navController.navigate(Screen.Register.route)}

            )

        }
        composable(Screen.Register.route){
            RegisterScreen(
                onRegisterSuccess = {navController.navigate(Screen.Home.route)},
                onLoginClick = {navController.navigate(Screen.Login.route)}

            )

        }
    }

}