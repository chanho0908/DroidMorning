package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    OutlinedButton(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(AppButtonDefaults.height),
        enabled = enabled,
        shape = AppButtonDefaults.shape,
        border = BorderStroke(1.dp, AppButtonDefaults.secondaryBorderColor(enabled)),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = AppButtonDefaults.secondaryContentColor(enabled = enabled),
                disabledContentColor = AppButtonDefaults.secondaryContentColor(enabled = enabled),
            ),
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(AppButtonDefaults.iconSize),
            )
            Spacer(modifier = Modifier.width(AppButtonDefaults.iconSpacing))
        }
        Text(
            text = text,
            style = AppButtonDefaults.textStyle,
        )
    }
}

@Preview
@Composable
private fun AppSecondaryButtonPreview() {
    AppTheme {
        AppSecondaryButton(
            text = "취소",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun AppSecondaryButtonDisabledPreview() {
    AppTheme {
        AppSecondaryButton(
            text = "비활성화",
            onClick = {},
            enabled = false,
        )
    }
}
