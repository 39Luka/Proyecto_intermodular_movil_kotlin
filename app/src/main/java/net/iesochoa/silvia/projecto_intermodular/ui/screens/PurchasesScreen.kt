package net.iesochoa.silvia.projecto_intermodular.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.iesochoa.silvia.projecto_intermodular.model.PurchasesUiState
import net.iesochoa.silvia.projecto_intermodular.ui.components.*
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.TextPrimary
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla de Historial de Compras.
 * Se ha eliminado el FAB redundante y se ha optimizado el área de scroll.
 */
@OptIn(ExperimentalMaterial3Api::class)
/**
 * Pantalla que muestra el historial de pedidos del usuario.
 * Permite filtrar pedidos por fecha y visualizar su estado actual.
 */
@Composable
fun PurchasesScreen(
    uiState: PurchasesUiState,
    onDateRangeSelected: (Long?, Long?) -> Unit,
    onPurchaseClick: (Int) -> Unit,
    onBackClick: (() -> Unit)?,
    onProfileClick: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit
) {
    var showDateRangePicker by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState()
    val listState = rememberLazyListState()

    // Scroll al principio al cambiar de página
    LaunchedEffect(uiState.currentPage) {
        listState.animateScrollToItem(0)
    }

    // Diálogo del Selector de Fechas
    if (showDateRangePicker) {
        Dialog(
            onDismissRequest = { showDateRangePicker = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showDateRangePicker = false }) {
                            Text("Cerrar")
                        }
                        TextButton(onClick = {
                            onDateRangeSelected(
                                dateRangePickerState.selectedStartDateMillis,
                                dateRangePickerState.selectedEndDateMillis
                            )
                            showDateRangePicker = false
                        }) {
                            Text("Aplicar")
                        }
                    }
                    DateRangePicker(
                        state = dateRangePickerState,
                        modifier = Modifier.weight(1f),
                        title = { Text(modifier = Modifier.padding(16.dp), text = "Periodo de búsqueda") }
                    )
                }
            }
        }
    }

    // Usamos una Column simple para evitar conflictos de padding de Scaffold
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp) // Espacio para la BottomBar del sistema
        ) {
            item {
                ScreenHeader(
                    title = "Mis compras",
                    showSearch = true,
                    showFilter = true,
                    searchQuery = formatSelectedDates(uiState.startDate, uiState.endDate),
                    searchPlaceholder = "Filtrar por fecha...",
                    onSearchChange = { /* Solo lectura */ },
                    onSearchClick = { showDateRangePicker = true },
                    onFilterClick = { showDateRangePicker = true },
                    onBackClick = onBackClick,
                    onProfileClick = onProfileClick,
                    profileImage = uiState.userProfileImage
                )
            }

            item {
                PageIntro(
                    eyebrow = "Historial",
                    title = "Tus pedidos.",
                    description = "Consulta el estado y detalle de tus compras de forma organizada."
                )
            }

            if (uiState.startDate != null) {
                item {
                    ActiveFilterLabel(
                        startDate = uiState.startDate,
                        endDate = uiState.endDate,
                        onClear = { onDateRangeSelected(null, null) }
                    )
                }
            }

            if (uiState.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Primary500)
                    }
                }
            }

            if (uiState.error != null) {
                item {
                    ErrorMessage(message = uiState.error)
                }
            }

            if (!uiState.isLoading && uiState.pedidos.isEmpty() && uiState.error == null) {
                item {
                    EmptyStateMessage(isFiltered = uiState.startDate != null)
                }
            }

            item {
                PedidoList(
                    pedidos = uiState.pedidos,
                    onItemClick = { onPurchaseClick(it.id) }
                )
            }

            item {
                PaginationControls(
                    currentPage = uiState.currentPage,
                    totalPages = uiState.totalPages,
                    onPreviousClick = onPreviousPage,
                    onNextClick = onNextPage,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private fun formatSelectedDates(start: Long?, end: Long?): String {
    if (start == null) return ""
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val startStr = sdf.format(Date(start))
    val endStr = end?.let { sdf.format(Date(it)) } ?: ""
    return if (endStr.isNotEmpty()) "$startStr - $endStr" else startStr
}

@Composable
private fun ActiveFilterLabel(startDate: Long, endDate: Long?, onClear: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { liveRegion = LiveRegionMode.Polite },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val rangeText = if (endDate != null) {
            "Del ${sdf.format(Date(startDate))} al ${sdf.format(Date(endDate))}"
        } else {
            "Día: ${sdf.format(Date(startDate))}"
        }
        
        Text(
            text = "Mostrando: $rangeText",
            style = AppTypography.bodySmall,
            color = Primary500
        )
        TextButton(onClick = onClear) {
            Text("Quitar filtro")
        }
    }
}

@Composable
private fun EmptyStateMessage(isFiltered: Boolean) {
    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(
            text = if (!isFiltered) "Aún no tienes compras." else "No hay pedidos para este periodo.",
            style = AppTypography.bodyMedium,
            color = TextPrimary.copy(alpha = 0.6f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
