package com.example.kawa.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // Use the lighter green for primary elements so they stand out against dark backgrounds
    primary = green,
    onPrimary = green, // Text on primary buttons should be dark green for contrast

    // Secondary can be the peach color for accents
    secondary = light_colour,
    onSecondary = green,
    // Tertiary for subtle highlights
    tertiary = green,
    // Backgrounds
    background = black,
    onBackground = white,
    surface = Color(0xFF121212), // Slightly lighter than pure black for cards
    onSurface = white
)

private val LightColorScheme = lightColorScheme(
    // Use the strong brand green for primary action buttons
    primary = green,
    onPrimary = green_light, // Text on green buttons should be white

    // Secondary accents using the light green
    secondary = green_light,
    onSecondary = green,

    // Use your brand's specific "light_colour" (peach) for backgrounds
    // to give the app a unique brand identity, rather than generic white.
    background = white,
    onBackground = black,

    surface = white,
    onSurface = black,

    tertiary = black
)

@Composable
fun KawaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
