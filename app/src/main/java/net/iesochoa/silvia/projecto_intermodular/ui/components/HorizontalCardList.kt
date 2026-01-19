import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCard
import net.iesochoa.silvia.projecto_intermodular.ui.components.HorizontalCardItem

@Composable
fun HorizontalCardList(
    items: List<HorizontalCardItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp) // separación vertical entre tarjetas
    ) {
        items.forEach { item ->
            HorizontalCard(
                title = item.title,
                description = item.description.takeIf { it.isNotEmpty() },
                leftLabel = item.leftLabel,
                leftValue = item.leftValue,
                rightLabel = item.rightLabel,
                rightValue = item.rightValue,
                imageRes = item.imageRes,
                modifier = Modifier.fillMaxWidth() // ocupa todo el ancho de la pantalla
            )
        }
    }
}
