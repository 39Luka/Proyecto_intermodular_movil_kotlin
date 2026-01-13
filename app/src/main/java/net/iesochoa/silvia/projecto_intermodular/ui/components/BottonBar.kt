package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.navigation.Screen
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BackgroundColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BorderColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary600
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary500
@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Screen.Home to R.drawable.home,
        Screen.Catalog to R.drawable.catalog,
        Screen.Offers to R.drawable.offers,
        Screen.Purchases to R.drawable.purchase,
        Screen.Cart to R.drawable.cart
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(BackgroundColor)
            .navigationBarsPadding()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { (screen, drawableId) ->
            val isSelected = navController.currentDestination?.route == screen.route

            Image(
                painter = painterResource(id = drawableId),
                contentDescription = screen.route,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (!isSelected) {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                colorFilter = ColorFilter.tint(
                    if (isSelected) Primary600 else Secondary500
                )
            )
        }
    }
}
