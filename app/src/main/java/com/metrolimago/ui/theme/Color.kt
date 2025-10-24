package com.metrolimago.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

// ===========================================
// Paleta principal de la marca MetroLima
// ===========================================
val MetroLimaPurple = Color(0xFF6A3DE8)
val MetroLimaGreen = Color(0xFF4CAF50)
val MetroLimaBlue = Color(0xFF42A5F5) // Para gradientes o acentos

// ===========================================
// Colores neutrales (fondos y texto)
// ===========================================
val TextPrimary = Color(0xFF212121)        // Texto principal (oscuro)
val TextSecondary = Color(0xFF757575)      // Texto secundario (gris)
val BackgroundLight = Color(0xFFFFFFFF)    // Fondo claro
val CardBackground = Color(0xFFF5F5F5)     // Fondo de tarjetas
val DisabledGray = Color(0xFFE0E0E0)       // Elementos deshabilitados

// ===========================================
// Esquemas de color para Material 3
// ===========================================

// Tema claro 🌞
val LightColorScheme = lightColorScheme(
    primary = MetroLimaPurple,
    secondary = MetroLimaGreen,
    background = BackgroundLight,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

// Tema oscuro 🌙
val DarkColorScheme = darkColorScheme(
    primary = MetroLimaPurple,
    secondary = MetroLimaGreen,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0)
)
