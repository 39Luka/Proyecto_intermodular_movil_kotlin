package net.iesochoa.silvia.projecto_intermodular.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import net.iesochoa.silvia.projecto_intermodular.R
import net.iesochoa.silvia.projecto_intermodular.ui.theme.AppTypography
import net.iesochoa.silvia.projecto_intermodular.ui.theme.BorderColor
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Neutral100
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Primary600
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Secondary600
import net.iesochoa.silvia.projecto_intermodular.ui.utils.decodeBase64ToBitmap

@Composable
fun ScreenHeader(
    title: String? = null,
    onBackClick: (() -> Unit)? = null,
    onProfileClick: (() -> Unit)? = null,
    profileImage: String? = null,
    showSearch: Boolean = false,
    showFilter: Boolean = false,
    searchQuery: String = "",
    onSearchChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        border = BorderStroke(1.dp, BorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                            .size(30.dp)
                            .clickable { onBackClick() }
                    )
                } else {
                    Spacer(modifier = Modifier.size(30.dp))
                }

                if (title != null) {
                    Text(
                        text = title,
                        style = AppTypography.titleLarge,
                        color = Secondary600,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                if (onProfileClick != null) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .clickable { onProfileClick() }
                    ) {
                        val cleanImage = profileImage?.trim()?.replace("\n", "")?.replace("\r", "")
                        
                        if (!cleanImage.isNullOrEmpty()) {
                            val bitmap = cleanImage.decodeBase64ToBitmap()
                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.profile),
                                    contentDescription = "Perfil",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Perfil",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
else if (onBackClick != null) {
                    // Si hay botón de atrás pero no de perfil, ponemos un espacio para centrar el título
                    Spacer(modifier = Modifier.size(30.dp))
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
                        modifier = Modifier.weight(1f),
                        cornerRadius = 999.dp,
                        textColor = Primary600
                    )

                    if (showFilter) {
                        Image(
                            painter = painterResource(id = R.drawable.filter),
                            contentDescription = "Filtro",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { onFilterClick() }
                        )
                    }
                }
            }
        }
    }
}
