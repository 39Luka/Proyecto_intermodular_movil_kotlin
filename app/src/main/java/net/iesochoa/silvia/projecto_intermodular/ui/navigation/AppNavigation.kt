package net.iesochoa.silvia.projecto_intermodular.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.iesochoa.silvia.projecto_intermodular.ui.screens.CartScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.CatalogScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.HomeScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.LoginScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.OffersScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.PurchasesScreen
import net.iesochoa.silvia.projecto_intermodular.ui.screens.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController,
                  modifier: Modifier = Modifier){

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

        composable(Screen.Home.route){
            HomeScreen()
        }

        composable(Screen.Catalog.route) {
            CatalogScreen()
        }

        composable(Screen.Offers.route){
            OffersScreen()

        }

        composable(Screen.Purchases.route) {
            PurchasesScreen()
        }

        composable(Screen.Cart.route) {
            CartScreen()
        }
    }

}