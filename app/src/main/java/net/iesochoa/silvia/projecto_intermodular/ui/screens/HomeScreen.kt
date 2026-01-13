import androidx.compose.runtime.Composable
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardItem
import net.iesochoa.silvia.projecto_intermodular.ui.components.CardList
import net.iesochoa.silvia.projecto_intermodular.ui.components.CustomCard
import net.iesochoa.silvia.projecto_intermodular.R
@Composable
fun HomeScreen() {

    val cards = listOf(
        CardItem(
            imageRes = R.drawable.ic_launcher_foreground,
            title = "Panadería",
            bottomText1 = "Antes 2,50€",
            bottomText2 = "Ahora 1,80€"
        ),
        CardItem(
            imageRes = R.drawable.ic_launcher_foreground,
            title = "Bollería",
            bottomText1 = null,
            bottomText2 = "Desde 1,20€"
        ),
        CardItem(
            imageRes = R.drawable.ic_launcher_foreground,
            title = "Tartas",
            bottomText1 = "Oferta limitada",
            bottomText2 = "Ver más"
        )
    )

    CardList(items = cards)
}

