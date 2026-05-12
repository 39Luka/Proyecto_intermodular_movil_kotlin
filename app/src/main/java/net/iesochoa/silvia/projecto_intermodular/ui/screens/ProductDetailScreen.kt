// ui/screens/ProductDetailScreen.kt
package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.iesochoa.silvia.projecto_intermodular.data.AuthRepository
import net.iesochoa.silvia.projecto_intermodular.data.CartRepository
import net.iesochoa.silvia.projecto_intermodular.data.Product
import net.iesochoa.silvia.projecto_intermodular.data.ProductRepository
import net.iesochoa.silvia.projecto_intermodular.ui.components.PrimaryButton
import net.iesochoa.silvia.projecto_intermodular.ui.components.ScreenHeader
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*
import java.util.Locale
import javax.inject.Inject

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddedToCart: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        ScreenHeader(
            title = "Detalle",
            onBackClick = onBackClick,
            profileImage = uiState.userProfileImage
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary500)
                }
            }

            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = Error600)
                }
            }

            uiState.product != null -> {
                ProductDetailContent(
                    product = uiState.product!!,
                    quantity = uiState.quantity,
                    onQuantityChange = { viewModel.updateQuantity(it) },
                    onAddToCart = { 
                        viewModel.addToCart()
                        onAddedToCart()
                    }
                )
            }
        }
    }
}

@Composable
private fun ProductDetailContent(
    product: Product,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 🔹 Product Card Wrapper
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(32.dp), spotColor = Color(0x14000000)),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Neutral100),
            border = BorderStroke(1.dp, Neutral200)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // 🔹 Figure / Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Neutral100, Primary100.copy(alpha = 0.6f))
                            )
                        )
                ) {
                    AsyncImage(
                        model = product.getDisplayImage(),
                        contentDescription = product.getDisplayTitle(),
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🔹 Content Copy
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = product.getCategoryName().uppercase(),
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = Secondary500
                    )

                    Text(
                        text = product.getDisplayTitle(),
                        style = AppTypography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = TextPrimary,
                        lineHeight = 38.sp
                    )

                    Surface(
                        color = Secondary100,
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "Recién preparado",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = AppTypography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = Secondary600
                        )
                    }

                    Text(
                        text = product.description ?: "Sin descripción disponible.",
                        style = AppTypography.bodyLarge,
                        color = TextPrimary.copy(alpha = 0.7f),
                        lineHeight = 28.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🔹 Action Card (Price, Stock, Quantity, Button)
                ActionSection(
                    product = product,
                    quantity = quantity,
                    onQuantityChange = onQuantityChange,
                    onAddToCart = onAddToCart
                )
            }
        }
    }
}

@Composable
private fun ActionSection(
    product: Product,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onAddToCart: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Price Stat
            StatCard(
                label = "Precio",
                value = "€${String.format(Locale.US, "%.2f", product.price ?: 0.0)}",
                modifier = Modifier.weight(1f)
            )

            // Stock Stat
            val stock = product.stock ?: 0
            StatCard(
                label = "Disponibilidad",
                value = if (stock > 0) "$stock unidades" else "Agotado",
                modifier = Modifier.weight(1f),
                valueColor = if (stock > 0) Secondary500 else Error600
            )
        }

        if ((product.stock ?: 0) > 0) {
            // Quantity Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Neutral200, RoundedCornerShape(18.dp))
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CANTIDAD",
                    modifier = Modifier.padding(start = 12.dp),
                    style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary.copy(alpha = 0.5f)
                )
                
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar", tint = Primary500)
                }

                Text(
                    text = quantity.toString(),
                    style = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                IconButton(onClick = { if (quantity < (product.stock ?: 0)) onQuantityChange(quantity + 1) }) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar", tint = Primary500)
                }
            }
        }

        PrimaryButton(
            text = if ((product.stock ?: 0) > 0) "Añadir al carrito" else "Agotado",
            onClick = onAddToCart,
            enabled = (product.stock ?: 0) > 0
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Secondary500
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = BorderStroke(1.dp, Neutral200.copy(alpha = 0.7f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label.uppercase(),
                style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary.copy(alpha = 0.5f)
            )
            Text(
                text = value,
                style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = valueColor
            )
        }
    }
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        observeUser()
    }

    private fun observeUser() {
        viewModelScope.launch {
            authRepository.getUser().collect { user ->
                _uiState.update { it.copy(userProfileImage = user?.profileImageBase64) }
            }
        }
    }

    fun loadProduct(productId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val product = productRepository.getProductById(productId)
                _uiState.value = _uiState.value.copy(
                    product = product,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar producto"
                )
            }
        }
    }

    fun updateQuantity(quantity: Int) {
        _uiState.value = _uiState.value.copy(quantity = quantity)
    }

    fun addToCart() {
        val product = _uiState.value.product ?: return
        val quantity = _uiState.value.quantity
        cartRepository.addToCart(product, quantity)
        _uiState.value = _uiState.value.copy(
            quantity = 1,
            addedToCart = true
        )
    }
}

data class ProductDetailUiState(
    val product: Product? = null,
    val quantity: Int = 1,
    val userProfileImage: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val addedToCart: Boolean = false
)
