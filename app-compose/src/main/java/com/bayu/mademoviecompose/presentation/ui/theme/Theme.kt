package com.bayu.mademoviecompose.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = ElectricPink,
    primaryVariant = ElectricPink,
    secondary = ElectricPink,
    secondaryVariant = ElectricPink,
    onPrimary = Color.White,
    onSecondary = Color.White,
    background = DarkGunmetal,
    onBackground = Color.White,
    surface = DarkGunmetal,
    onSurface = Color.White,
)


@Composable
fun MADEMovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}