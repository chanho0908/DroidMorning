package com.peto.droidmorning.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.OnPrimary
import com.peto.droidmorning.designsystem.theme.Shape

object AppButtonDefaults {
    private const val DISABLED_CONTENT_ALPHA = 0.5f
    private const val DISABLED_BACKGROUND_ALPHA = 0.3f

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
    fun primaryButtonBackgroundBrush(enabled: Boolean = true): Brush {
        val alpha = if (enabled) 1f else DISABLED_BACKGROUND_ALPHA
        val primaryLight = MaterialTheme.colorScheme.primaryContainer
        val primary = MaterialTheme.colorScheme.primary
        val primaryOrange = MaterialTheme.colorScheme.tertiary

        return Brush.horizontalGradient(
            colorStops =
                arrayOf(
                    0.0f to primaryLight.copy(alpha = alpha),
                    0.5f to primary.copy(alpha = alpha),
                    1.0f to primaryOrange.copy(alpha = alpha),
                ),
        )
    }

    @Composable
    fun primaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            OnPrimary
        } else {
            OnPrimary.copy(alpha = DISABLED_CONTENT_ALPHA)
        }

    @Composable
    fun secondaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
        }

    @Composable
    fun secondaryBorderColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = DISABLED_CONTENT_ALPHA)
        }

    val shape = Shape.buttonMedium

    val iconSize: Dp = Dimen.iconSm

    val iconSpacing: Dp = Dimen.spacingSm
}
