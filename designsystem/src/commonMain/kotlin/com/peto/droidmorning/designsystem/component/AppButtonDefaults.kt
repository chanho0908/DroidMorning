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
import com.peto.droidmorning.designsystem.theme.Primary
import com.peto.droidmorning.designsystem.theme.PrimaryLight
import com.peto.droidmorning.designsystem.theme.PrimaryOrange
import com.peto.droidmorning.designsystem.theme.Shape

object AppButtonDefaults {
    val height: Dp = Dimen.buttonHeightLg

    val heightSmall: Dp = Dimen.buttonHeightMd

    val heightLarge: Dp = Dimen.buttonHeightXl

    val textStyle: TextStyle
        @Composable
        get() =
            MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
            )

    fun primaryButtonBackgroundBrush(enabled: Boolean = true): Brush {
        val alpha = if (enabled) 1f else 0.3f

        return Brush.horizontalGradient(
            colorStops =
                arrayOf(
                    0.0f to PrimaryLight.copy(alpha = alpha),
                    0.5f to Primary.copy(alpha = alpha),
                    1.0f to PrimaryOrange.copy(alpha = alpha),
                ),
        )
    }

    @Composable
    fun primaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            OnPrimary
        } else {
            OnPrimary.copy(alpha = 0.5f)
        }

    @Composable
    fun secondaryContentColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        }

    @Composable
    fun secondaryBorderColor(enabled: Boolean = true): Color =
        if (enabled) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        }

    val shape = Shape.buttonMedium

    val iconSize: Dp = Dimen.iconSm

    val iconSpacing: Dp = Dimen.spacingSm

    const val DISABLED_ALPHA = 0.5f

    const val DISABLED_BACKGROUND_ALPHA = 0.3f
}
