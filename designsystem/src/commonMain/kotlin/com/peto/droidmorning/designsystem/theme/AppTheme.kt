package com.peto.droidmorning.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = droidMorningTypography(),
        shapes = DroidMorningShapes,
        content = content,
    )
}

private val LightColorScheme =
    lightColorScheme(
        primary = Primary,
        onPrimary = OnPrimary,
        primaryContainer = Accent,
        onPrimaryContainer = AccentForeground,
        secondary = Secondary,
        onSecondary = OnSecondary,
        secondaryContainer = Secondary,
        onSecondaryContainer = OnSecondary,
        tertiary = Warning,
        onTertiary = OnWarning,
        tertiaryContainer = Accent,
        onTertiaryContainer = AccentForeground,
        error = Error,
        onError = OnError,
        errorContainer = Color(0xFFFEE2E2),
        onErrorContainer = Color(0xFF7F1D1D),
        background = Background,
        onBackground = OnBackground,
        surface = Surface,
        onSurface = OnSurface,
        surfaceVariant = Muted,
        onSurfaceVariant = MutedForeground,
        outline = Border,
        outlineVariant = Border,
        scrim = Color.Black.copy(alpha = 0.32f),
        inverseSurface = OnBackground,
        inverseOnSurface = Background,
        inversePrimary = PrimaryDark,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        primaryContainer = AccentDark,
        onPrimaryContainer = AccentForegroundDark,
        secondary = Color(0xFF3D3530),
        onSecondary = Color(0xFFD9D0C5),
        secondaryContainer = Color(0xFF3D3530),
        onSecondaryContainer = Color(0xFFD9D0C5),
        tertiary = Color(0xFFFBBF24),
        onTertiary = OnPrimaryDark,
        tertiaryContainer = AccentDark,
        onTertiaryContainer = AccentForegroundDark,
        error = Color(0xFFF87171),
        onError = OnError,
        errorContainer = Color(0xFF7F1D1D),
        onErrorContainer = Color(0xFFFEE2E2),
        background = BackgroundDark,
        onBackground = OnBackgroundDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = MutedDark,
        onSurfaceVariant = MutedForegroundDark,
        outline = BorderDark,
        outlineVariant = BorderDark,
        scrim = Color.Black.copy(alpha = 0.5f),
        inverseSurface = OnBackgroundDark,
        inverseOnSurface = BackgroundDark,
        inversePrimary = Primary,
    )
