package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Shape

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    icon: ImageVector = Icons.Default.Error,
    iconTint: Color = MaterialTheme.colorScheme.error,
    iconBackgroundColor: Color = MaterialTheme.colorScheme.errorContainer,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        ConfirmDialogContent(
            title = title,
            message = message,
            confirmText = confirmText,
            cancelText = cancelText,
            icon = icon,
            iconTint = iconTint,
            iconBackgroundColor = iconBackgroundColor,
            onConfirm = onConfirm,
            onCancel = onDismissRequest,
        )
    }
}

@Composable
private fun ConfirmDialogContent(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    icon: ImageVector,
    iconTint: Color,
    iconBackgroundColor: Color,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth(0.85f)
                .clip(Shape.card),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Dimen.spacingXl),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(72.dp)
                        .clip(androidx.compose.foundation.shape.CircleShape)
                        .background(iconBackgroundColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(40.dp),
                )
            }

            Spacer(modifier = Modifier.height(Dimen.spacingLg))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.height(Dimen.spacingSm))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Dimen.spacingXl))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AppSecondaryButton(
                    text = cancelText,
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(Dimen.spacingSm))

                AppPrimaryButton(
                    text = confirmText,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    AppTheme {
        ConfirmDialogContent(
            title = "답변 삭제",
            message = "정말 이 답변을 삭제하시겠습니까?",
            confirmText = "삭제",
            cancelText = "취소",
            icon = Icons.Default.Error,
            iconTint = MaterialTheme.colorScheme.error,
            iconBackgroundColor = MaterialTheme.colorScheme.errorContainer,
            onConfirm = {},
            onCancel = {},
        )
    }
}
