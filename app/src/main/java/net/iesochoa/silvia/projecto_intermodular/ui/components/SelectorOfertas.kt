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
    onSeleccionCambio: (Set<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var seleccionadas by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // 🔹 Chips de ofertas seleccionadas
        if (seleccionadas.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                seleccionadas.forEach { id ->
                    val oferta = ofertas.firstOrNull { it.id == id }
                    oferta?.let {
                        OfertaChip(
                            text = it.nombre,
                            onRemove = {
                                seleccionadas -= id
                                onSeleccionCambio(seleccionadas)
                            }
                        )
                    }
                }
            }
        }

        // 🔹 Botón principal
        PrimaryButton(
            text = if (seleccionadas.isEmpty())
                "Seleccionar ofertas"
            else
                "Editar ofertas",
            onClick = { expanded = true }
        )

        // 🔹 Dropdown
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            ofertas.forEach { oferta ->
                DropdownMenuItem(
                    text = {
                        Text("${oferta.nombre} (${oferta.descuento * 100}%)")
                    },
                    onClick = {
                        seleccionadas =
                            if (seleccionadas.contains(oferta.id))
                                seleccionadas - oferta.id
                            else
                                seleccionadas + oferta.id

                        onSeleccionCambio(seleccionadas)
                        expanded = false // 👈 mejora aplicada
                    },
                    trailingIcon = {
                        if (seleccionadas.contains(oferta.id)) {
                            Text("✓")
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

