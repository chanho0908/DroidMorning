package com.peto.droidmorning.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Shape

object AppButtonDefaults {
    private const val CONTENT_ALPHA = 0.5f
    private const val BACKGROUND_ALPHA = 0.3f

    val height: Dp = Dimen.buttonHeightLg

    val heightSmall: Dp = Dimen.buttonHeightMd

    val heightLarge: Dp = Dimen.buttonHeightXl

    val textStyle: TextStyle
        @Composable
        get() =
            MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
            )

    @Composable
    fun primaryButtonBackgroundColor(enabled: Boolean = true): Color {
        val alpha = if (enabled) 1f else 0.5f
        return MaterialTheme.colorScheme.primary.copy(alpha = alpha)
    }

    @Composable
    fun primaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onPrimary.copy(alpha = CONTENT_ALPHA)
        }

    @Composable
    fun secondaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = CONTENT_ALPHA)
        }

    @Composable
    fun secondaryBorderColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = CONTENT_ALPHA)
        }

    val shape = Shape.buttonMedium

    val iconSize: Dp = Dimen.iconSm

    val iconSpacing: Dp = Dimen.spacingSm

    val elevation: Dp = Dimen.cardElevation

    val pressedElevation: Dp = 4.dp
}
