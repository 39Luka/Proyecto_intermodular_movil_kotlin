package net.iesochoa.silvia.projecto_intermodular.ui.navigation

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Register : Screen("register")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Catalog : Screen("catalog")
    object Offers : Screen("offers")
    object Product : Screen("product")
    object Purchases : Screen("purchases")
    object Cart : Screen("cart")
    object Purchase : Screen("purchase")
    object EditImage : Screen("edit_image")
    object EditPassword : Screen("edit_password")
}