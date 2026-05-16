package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.theme.*

/**
 * Componente de introducción para las pantallas principales.
 * Presenta una estructura con un texto superior corto (eyebrow), un título grande y una descripción.
 * Utiliza un fondo degradado lila suave y bordes redondeados pronunciados.
 *
 * @param eyebrow Texto pequeño superior (ej. categoría o sección).
 * @param title Título principal de la página.
 * @param description Texto explicativo detallado.
 * @param modifier Modificador de Compose.
 */
@Composable
fun PageIntro(
    eyebrow: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Neutral100.copy(alpha = 0.97f), Primary100.copy(alpha = 0.64f))
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, Primary200.copy(alpha = 0.55f))
    ) {
        Column(
            modifier = Modifier
                .background(gradient)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = eyebrow.uppercase(),
                style = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
                ),
                color = Primary500
            )

            Text(
                text = title,
                style = AppTypography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = TextPrimary,
                lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified
            )

            Text(
                text = description,
                style = AppTypography.bodyMedium,
                color = TextPrimary.copy(alpha = 0.7f),
                lineHeight = androidx.compose.ui.unit.TextUnit.Unspecified
            )
        }
    }
}
