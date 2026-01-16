package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    val shape = AppButtonDefaults.shape
    val backgroundColor = AppButtonDefaults.primaryButtonBackgroundColor(enabled = enabled)

    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .height(AppButtonDefaults.height),
        enabled = enabled,
        shape = shape,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = AppButtonDefaults.primaryContentColor(enabled = true),
                disabledContainerColor = backgroundColor,
                disabledContentColor = AppButtonDefaults.primaryContentColor(enabled = false),
            ),
        elevation =
            ButtonDefaults.buttonElevation(
                defaultElevation = AppButtonDefaults.elevation,
                pressedElevation = AppButtonDefaults.pressedElevation,
                disabledElevation = 0.dp,
            ),
    ) {
        if (icon != null) {
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
fun PrimaryButtonPreview() {
    AppTheme {
        AppPrimaryButton(
            text = "답변 작성하기",
            onClick = {},
        )
    }
}
