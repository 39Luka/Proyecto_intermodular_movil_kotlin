package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary100
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary600

data class Oferta(
    val id: String,
    val nombre: String,
    val descuento: Double // o porcentaje
)


@Composable
fun SelectorOfertas(
    ofertas: List<Oferta>,
    seleccionadas: Set<Oferta> = emptySet(),
    onSeleccionCambio: (Set<Oferta>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSet by remember { mutableStateOf(seleccionadas) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // 🔹 Chips de ofertas seleccionadas
        if (selectedSet.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedSet.forEach { oferta ->
                    OfertaChip(
                        text = oferta.nombre,
                        onRemove = {
                            selectedSet = selectedSet - oferta
                            onSeleccionCambio(selectedSet)
                        }
                    )
                }
            }
        }

        // 🔹 Botón principal
        PrimaryButton(
            text = if (selectedSet.isEmpty()) "Seleccionar ofertas" else "Editar ofertas",
            onClick = { expanded = true }
        )

        // 🔹 Dropdown
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            ofertas.forEach { oferta ->
                val estaSeleccionada = selectedSet.contains(oferta)
                DropdownMenuItem(
                    text = { Text("${oferta.nombre} (${(oferta.descuento * 100).toInt()}%)") },
                    onClick = {
                        selectedSet = if (estaSeleccionada) selectedSet - oferta else selectedSet + oferta
                        onSeleccionCambio(selectedSet)
                    },
                    trailingIcon = {
                        // 🔹 Chip pequeño como check visual
                        if (estaSeleccionada) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Primary600
                            ) {
                                Text(
                                    text = "✓",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = AppTypography.bodySmall
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun OfertaChip(
    text: String,
    onRemove: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Primary100
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = text,
                style = AppTypography.bodySmall,
                color = Primary600
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "✕",
                modifier = Modifier
                    .clickable { onRemove() }
                    .padding(2.dp),
                color = Primary600
            )
        }
    }
}

