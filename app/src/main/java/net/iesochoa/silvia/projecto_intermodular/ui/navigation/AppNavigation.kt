package net.iesochoa.silvia.projecto_intermodular.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import net.iesochoa.silvia.projecto_intermodular.ui.screens.*
import net.iesochoa.silvia.projecto_intermodular.viewmodel.*

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // Auth Screens
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            LoginScreen(
                uiState = uiState,
                onEmailChange = { viewModel.onEmailChange(it) },
                onPasswordChange = { viewModel.onPasswordChange(it) },
                onLoginClick = {
                    viewModel.login {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            RegisterScreen(
                uiState = uiState,
                onUsernameChange = { viewModel.onUsernameChange(it) },
                onEmailChange = { viewModel.onEmailChange(it) },
                onPasswordChange = { viewModel.onPasswordChange(it) },
                onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
                onRegisterClick = {
                    viewModel.register {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // Main Screens
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                onSearchChange = { viewModel.onSearchQueryChange(it) },
                onRetryClick = { viewModel.loadHomeData() },
                onProductClick = { productId ->
                    navController.navigate("${Screen.ProductDetail.route}/$productId")
                },
                onBackClick = null,
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Catalog.route) {
            val viewModel: CatalogViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            CatalogScreen(
                uiState = uiState,
                onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                onProductClick = { productId ->
                    navController.navigate("${Screen.ProductDetail.route}/$productId")
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onNextPage = { viewModel.goToNextPage() },
                onPreviousPage = { viewModel.goToPreviousPage() },
                onFilterClick = { viewModel.showFilterDialog() },
                onCategorySelect = { categoryId -> viewModel.selectCategory(categoryId) },
                onDismissFilter = { viewModel.hideFilterDialog() }
            )
        }

        composable(Screen.Offers.route) {
            val viewModel: OffersViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            OffersScreen(
                uiState = uiState,
                onSearchChange = { viewModel.onSearchQueryChange(it) },
                onProductClick = { productId ->
                    navController.navigate("${Screen.ProductDetail.route}/$productId")
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onNextPage = { viewModel.goToNextPage() },
                onPreviousPage = { viewModel.goToPreviousPage() }
            )
        }

        composable(Screen.Purchases.route) {
            val viewModel: PurchasesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            PurchasesScreen(
                uiState = uiState,
                onSearchChange = { viewModel.onSearchQueryChange(it) },
                onPurchaseClick = { purchaseId ->
                    navController.navigate("${Screen.PurchaseDetail.route}/$purchaseId")
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onNextPage = { viewModel.goToNextPage() },
                onPreviousPage = { viewModel.goToPreviousPage() }
            )
        }

        composable(Screen.Cart.route) {
            val viewModel: CartViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            CartScreen(
                uiState = uiState,
                onQuantityChange = { id, qty -> viewModel.updateQuantity(id, qty) },
                onRemoveItem = { viewModel.removeItem(it) },
                onPromotionSelected = { productId, promoId -> 
                    viewModel.onPromotionSelected(productId, promoId)
                },
                onCheckoutClick = { 
                    viewModel.checkout {
                        navController.navigate(Screen.Purchases.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                },
                onBackClick = { navController.popBackStack() },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            ProfileScreen(
                uiState = uiState,
                onChangeImageClick = { navController.navigate(Screen.EditImage.route) },
                onChangePasswordClick = { navController.navigate(Screen.EditPassword.route) },
                onLogoutClick = {
                    viewModel.logout {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.EditImage.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val context = androidx.compose.ui.platform.LocalContext.current
            ChangeImageScreen(
                uiState = uiState,
                onImageSelected = { viewModel.onImageSelected(it) },
                onSaveClick = { 
                    viewModel.updateImage(context.contentResolver) {
                        navController.popBackStack()
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.EditPassword.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            ChangePasswordScreen(
                uiState = uiState,
                onCurrentPasswordChange = { viewModel.onCurrentPasswordChange(it) },
                onNewPasswordChange = { viewModel.onNewPasswordChange(it) },
                onRepeatPasswordChange = { viewModel.onRepeatPasswordChange(it) },
                onSaveClick = { 
                    viewModel.updatePassword {
                        navController.popBackStack()
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Product Detail Screen
        composable(
            route = "${Screen.ProductDetail.route}/{productId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onAddedToCart = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        // Purchase Detail Screen
        composable(
            route = "${Screen.PurchaseDetail.route}/{purchaseId}",
            arguments = listOf(
                navArgument("purchaseId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val purchaseId = backStackEntry.arguments?.getInt("purchaseId") ?: return@composable
            PurchaseDetailScreen(
                purchaseId = purchaseId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
