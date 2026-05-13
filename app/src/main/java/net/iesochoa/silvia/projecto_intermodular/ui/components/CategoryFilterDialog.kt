package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.data.Category
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

@Composable
fun CategoryFilterDialog(
    categories: List<Category>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        containerColor = Neutral100,
        title = {
            Text(
                text = "Filtrar por categoría",
                style = AppTypography.titleMedium,
                color = Secondary600
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryFilterItem(
                        name = "Todas las categorías",
                        isSelected = selectedCategoryId == null,
                        onClick = {
                            onCategorySelected(null)
                            onDismiss()
                        }
                    )
                }
                items(categories) { category ->
                    CategoryFilterItem(
                        name = category.name ?: "Sin nombre",
                        isSelected = category.id == selectedCategoryId,
                        onClick = {
                            onCategorySelected(category.id)
                            onDismiss()
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Primary500
                )
            ) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
private fun CategoryFilterItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary100 else Neutral200
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, Primary500)
        } else {
            null
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = AppTypography.labelLarge,
                color = if (isSelected) Primary500 else Secondary600,
                modifier = Modifier.weight(1f)
            )
            
            if (isSelected) {
                Text(
                    text = "✓",
                    style = AppTypography.titleMedium,
                    color = Primary500
                )
            }
        }
    }
}
