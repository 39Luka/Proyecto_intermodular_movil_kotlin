package net.iesochoa.silvia.projecto_intermodular.ui.navigation

import ChangeImageScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.iesochoa.silvia.projecto_intermodular.ui.screens.*
import net.iesochoa.silvia.projecto_intermodular.viewmodel.*

fun NavHostController.getBackAction(): (() -> Unit)? =
    this.previousBackStackEntry?.let { { this.popBackStack() } }

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val catalogViewModel: CatalogViewModel = viewModel()
    val offersViewModel: OffersViewModel = viewModel()
    val purchasesViewModel: PurchasesViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {

        composable(Screen.Login.route) {
            val uiState = loginViewModel.uiState.collectAsState().value
            LoginScreen(
                uiState = uiState,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onLoginClick = {
                    loginViewModel.login {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            val uiState = registerViewModel.uiState.collectAsState().value
            RegisterScreen(
                uiState = uiState,
                onUsernameChange = registerViewModel::onUsernameChange,
                onEmailChange = registerViewModel::onEmailChange,
                onPasswordChange = registerViewModel::onPasswordChange,
                onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
                onRegisterClick = {
                    registerViewModel.register {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }

        // Pantallas principales
        composable(Screen.Home.route) {
            val uiState = homeViewModel.uiState.collectAsState().value
            HomeScreen(
                uiState = uiState,
                onSearchChange = homeViewModel::onSearchQueryChange,
                onBackClick = navController.getBackAction(),
                onProfileClick = { navController.navigate(Screen.Profile.route) } // 🔹 lambda perfil
            )
        }

        composable(Screen.Catalog.route) {
            val uiState = catalogViewModel.uiState.collectAsState().value
            CatalogScreen(
                uiState = uiState,
                onSearchQueryChange = catalogViewModel::onSearchQueryChange,
                onBackClick = navController.getBackAction(),
                onProfileClick = { navController.navigate(Screen.Profile.route) } // 🔹 lambda perfil
            )
        }

        composable(Screen.Offers.route) {
            val uiState = offersViewModel.uiState.collectAsState().value
            OffersScreen(
                uiState = uiState,
                onSearchChange = offersViewModel::onSearchQueryChange,
                onBackClick = navController.getBackAction(),
                onProfileClick = { navController.navigate(Screen.Profile.route) } // 🔹 lambda perfil
            )
        }

        composable(Screen.Purchases.route) {
            val uiState = purchasesViewModel.uiState.collectAsState().value
            PurchasesScreen(
                uiState = uiState,
                onSearchChange = purchasesViewModel::onSearchQueryChange,
                onBackClick = navController.getBackAction(),
                onProfileClick = { navController.navigate(Screen.Profile.route) } // 🔹 lambda perfil
            )
        }

        composable(Screen.Cart.route) {
            val uiState = cartViewModel.uiState.collectAsState().value
            CartScreen(
                uiState = uiState,
                onOfertasSeleccionadas = cartViewModel::onOfertasSeleccionadas,
                onBackClick = navController.getBackAction(),
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )

        }


        // 🔹 Perfil
        composable(Screen.Profile.route) {
            val uiState = profileViewModel.uiState.collectAsState().value
            ProfileScreen(
                uiState = uiState,
                onChangeImageClick = { navController.navigate(Screen.EditImage.route) },
                onChangePasswordClick = { navController.navigate(Screen.EditPassword.route) },
                onLogoutClick = { profileViewModel.logout { navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                } } },
                onBackClick = navController.getBackAction()
            )
        }

        composable(Screen.EditImage.route) {
            val uiState = profileViewModel.uiState.collectAsState().value
            ChangeImageScreen(
                uiState = uiState,
                onImagePathChange = profileViewModel::onNewImagePathChange,
                onSaveClick = { profileViewModel.changeImage { navController.popBackStack() } },
                onBackClick = navController.getBackAction()
            )
        }

        composable(Screen.EditPassword.route) {
            val uiState = profileViewModel.uiState.collectAsState().value
            ChangePasswordScreen(
                uiState = uiState,
                onNewPasswordChange = profileViewModel::onNewPasswordChange,
                onRepeatPasswordChange = profileViewModel::onRepeatPasswordChange,
                onSaveClick = { profileViewModel.changePassword { navController.popBackStack() } },
                onBackClick = navController.getBackAction()
            )
        }

    }
}
