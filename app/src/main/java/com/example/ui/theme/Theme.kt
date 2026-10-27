package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = BrandGreen,
  onPrimary = Color.Black,
  primaryContainer = BrandGreenDark,
  onPrimaryContainer = Color.White,
  secondary = BrandGreen,
  onSecondary = Color.Black,
  tertiary = AccentAmber,
  background = DarkBackground,
  onBackground = TextPrimary,
  surface = DarkSurface,
  onSurface = TextPrimary,
  surfaceVariant = DarkCard,
  onSurfaceVariant = TextSecondary,
  outline = DarkBorder,
  error = AccentRed,
  onError = Color.White
)

@Composable
fun ForexBotTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}
