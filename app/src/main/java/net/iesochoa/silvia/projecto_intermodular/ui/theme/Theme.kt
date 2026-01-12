package net.iesochoa.silvia.projecto_intermodular.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Primary500,
    onPrimary = TextOnPrimary,
    secondary = Secondary500,
    onSecondary = TextOnPrimary,
    tertiary = Success500,
    onTertiary = TextOnPrimary,
    background = Neutral1000,
    onBackground = Neutral100,
    surface = Neutral900,
    onSurface = Neutral100,
    error = Error500,
    onError = TextOnPrimary
)

// Tema claro
private val LightColorScheme = lightColorScheme(
    primary = Primary300,
    onPrimary = TextOnPrimary,
    secondary = Secondary300,
    onSecondary = TextOnPrimary,
    tertiary = Success300,
    onTertiary = TextOnPrimary,
    background = Neutral100,
    onBackground = Neutral1000,
    surface = Neutral200,
    onSurface = TextPrimary,
    error = Error500,
    onError = TextOnPrimary
)

@Composable
fun Projecto_IntermodularTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // tu tipografía personalizada
        content = content
    )
}
