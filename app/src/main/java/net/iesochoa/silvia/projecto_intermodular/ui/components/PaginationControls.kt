package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary500
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Neutral400

@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPreviousClick,
            enabled = currentPage > 0,
            modifier = Modifier.width(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentPage > 0) Primary500 else Neutral400,
                disabledContainerColor = Neutral400
            )
        ) {
            Text("← Anterior")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Página ${currentPage + 1} de $totalPages",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onNextClick,
            enabled = currentPage < totalPages - 1,
            modifier = Modifier.width(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentPage < totalPages - 1) Primary500 else Neutral400,
                disabledContainerColor = Neutral400
            )
        ) {
            Text("Siguiente →")
        }
    }
}
