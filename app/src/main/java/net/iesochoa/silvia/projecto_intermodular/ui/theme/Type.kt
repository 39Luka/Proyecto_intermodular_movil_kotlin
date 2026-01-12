package net.iesochoa.silvia.projecto_intermodular.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// FontFamily definidos en Fonts.kt
import net.iesochoa.silvia.projecto_intermodular.ui.theme.DMSans
import net.iesochoa.silvia.projecto_intermodular.ui.theme.Nunito

val AppTypography = Typography(
    // Títulos: Nunito
    displayLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 48.sp, lineHeight = 72.sp),
    displayMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 40.sp, lineHeight = 60.sp),
    displaySmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 51.sp),

    headlineLarge = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 42.sp),
    headlineMedium = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 36.sp),
    headlineSmall = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 20.sp, lineHeight = 30.sp),

    // Cuerpo: DM Sans
    bodyLarge = TextStyle(fontFamily = DMSans, fontWeight = FontWeight.Normal, fontSize = 20.sp, lineHeight = 30.sp),
    bodyMedium = TextStyle(fontFamily = DMSans, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodySmall = TextStyle(fontFamily = DMSans, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 21.sp),

    labelLarge = TextStyle(fontFamily = DMSans, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 18.sp),
    labelMedium = TextStyle(fontFamily = DMSans, fontWeight = FontWeight.Normal, fontSize = 10.sp, lineHeight = 15.sp)
)
