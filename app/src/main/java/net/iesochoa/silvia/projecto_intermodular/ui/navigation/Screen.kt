package net.iesochoa.silvia.projecto_intermodular.ui.navigation

/**
 * Representa las diferentes pantallas y rutas de navegación de la aplicación.
 * @property route Identificador único de la ruta de navegación.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Register : Screen("register")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Catalog : Screen("catalog")
    object Offers : Screen("offers")
    object ProductDetail : Screen("product_detail")
    object Purchases : Screen("purchases")
    object PurchaseDetail : Screen("purchase_detail")
    object Cart : Screen("cart")
    object EditImage : Screen("edit_image")
    object EditPassword : Screen("edit_password")
}
