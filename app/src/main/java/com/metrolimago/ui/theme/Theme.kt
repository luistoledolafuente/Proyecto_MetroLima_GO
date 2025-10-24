package com.metrolimago.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MetroLimaGOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Selecciona el esquema de color según el modo del sistema
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Aplica el esquema con Material 3
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
