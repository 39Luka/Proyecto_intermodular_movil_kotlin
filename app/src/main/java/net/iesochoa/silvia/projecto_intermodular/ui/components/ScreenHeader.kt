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
    showBack: Boolean = false,
    showProfile: Boolean = true,
    showSearch: Boolean = false,
    showFilter: Boolean = false,
    searchQuery: String = "",
    onSearchChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // 🔹 TOP ROW (Back / Title / Profile)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (showBack) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
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

            if (showProfile) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // 🔹 SEARCH + FILTER
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
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Filtro",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onFilterClick() }
                    )
                }
            }
        }
    }
}
