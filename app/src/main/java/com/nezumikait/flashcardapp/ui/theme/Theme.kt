package com.nezumikait.flashcardapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary          = Color(0xFF6DD5A8),
    secondary        = Color(0xFF4A9E8A),
    background       = Color(0xFF0D1B17),
    surface          = Color(0xFF122018),
    primaryContainer = Color(0xFF1A3D2E),
    secondaryContainer = Color(0xFF163328),
    onPrimaryContainer = Color(0xFFB8F0D8),
)

private val LightColorScheme = lightColorScheme(
    primary          = Color(0xFF1B6B4A),
    secondary        = Color(0xFF2E8B67),
    background       = Color(0xFFF4FBF7),
    surface          = Color(0xFFE8F6EE),
    primaryContainer = Color(0xFFD1F0E0),
    secondaryContainer = Color(0xFFBCE8D2),
    onPrimaryContainer = Color(0xFF0A3D25),
)

@Composable
fun FlashcardAppTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
