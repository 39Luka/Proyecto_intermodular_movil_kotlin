package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600

@Composable
fun ScreenHeader(
    title: String? = null,
    onBackClick: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,  // 🔹 Muestra icono solo si existe lambda
    showSearch: Boolean = false,
    showFilter: Boolean = false,
    searchQuery: String = "",
    onSearchChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (onBackClick != null) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onBackClick() }
                )
            } else {
                Spacer(modifier = Modifier.size(32.dp))
            }

            if (title != null) {
                Text(
                    text = title,
                    style = AppTypography.headlineSmall,
                    color = Secondary600
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            if (onProfileClick != null) {  // 🔹 Solo se muestra si hay lambda
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onProfileClick() }
                )
            }
        }

        if (showSearch) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchChange,
                    modifier = Modifier.weight(1f)
                )

                if (showFilter) {
                    Image(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filtro",
                        modifier = Modifier
                            .size(45.dp)
                            .clickable { onFilterClick() }
                    )
                }
            }
        }
    }
}
