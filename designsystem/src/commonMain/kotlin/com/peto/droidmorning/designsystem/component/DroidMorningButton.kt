package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.OnPrimary
import com.peto.droidmorning.designsystem.theme.Primary
import com.peto.droidmorning.designsystem.theme.PrimaryLight
import com.peto.droidmorning.designsystem.theme.PrimaryOrange
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    val shape = Shape.buttonMedium
    val backgroundBrush = primaryButtonBackgroundBrush(enabled = enabled)

    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(Dimen.buttonHeightLg)
                .clip(shape)
                .background(backgroundBrush),
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = OnPrimary,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = OnPrimary.copy(alpha = 0.5f),
            ),
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Dimen.iconSm),
            )
            Spacer(modifier = Modifier.width(Dimen.spacingSm))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

private fun primaryButtonBackgroundBrush(enabled: Boolean): Brush {
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

@Preview
@Composable
fun PrimaryButtonPreview() {
    AppTheme {
        PrimaryButton(
            text = "답변 작성하기",
            onClick = {},
        )
    }
}
