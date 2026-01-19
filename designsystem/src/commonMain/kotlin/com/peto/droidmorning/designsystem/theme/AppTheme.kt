package com.peto.droidmorning.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Extended colors for DroidMorning app
 */
data class ExtendedColors(
    val examSelected: Color = ExamSelected,
    val examCorrect: Color = ExamCorrect,
    val examCorrectBackground: Color = ExamCorrectBackground,
)

private val LightExtendedColors =
    ExtendedColors(
        examSelected = ExamSelected,
        examCorrect = ExamCorrect,
        examCorrectBackground = ExamCorrectBackground,
    )

private val DarkExtendedColors =
    ExtendedColors(
        examSelected = ExamSelectedDark,
        examCorrect = ExamCorrectDark,
        examCorrectBackground = ExamCorrectBackgroundDark,
    )

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme,
    content: @Composable () -> Unit,
) {
    val extendedColors = if (useDarkTheme) DarkExtendedColors else LightExtendedColors

    androidx.compose.runtime.CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = droidMorningTypography(),
            shapes = Shapes,
            content = content,
        )
    }
}

/**
 * Extension property to access extended colors
 */
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current

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
        tertiaryContainer = WarningContainer,
        onTertiaryContainer = OnWarningContainer,
        error = Error,
        onError = OnError,
        errorContainer = ErrorContainer,
        onErrorContainer = OnErrorContainer,
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
        tertiary = WarningDark,
        onTertiary = OnWarningDark,
        tertiaryContainer = WarningContainerDark,
        onTertiaryContainer = OnWarningContainerDark,
        error = ErrorDark,
        onError = OnErrorDark,
        errorContainer = ErrorContainerDark,
        onErrorContainer = OnErrorContainerDark,
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
