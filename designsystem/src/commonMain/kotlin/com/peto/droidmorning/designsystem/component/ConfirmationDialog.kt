package com.peto.droidmorning.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.peto.droidmorning.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
    confirmText: String = "확인",
    dismissText: String = "취소",
    isDestructive: Boolean = false,
) {
    val confirmColor =
        if (isDestructive) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        }

    AppAlertDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        text = message,
        icon = if (isDestructive) Icons.Default.Warning else null,
        iconTint = confirmColor,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                },
                colors =
                    ButtonDefaults.textButtonColors(
                        contentColor = confirmColor,
                    ),
            ) {
                Text(
                    text = confirmText,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = dismissText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
    )
}

@Preview
@Composable
private fun ConfirmationDialogPreview() {
    AppTheme {
        ConfirmationDialog(
            onDismissRequest = {},
            onConfirm = {},
            title = "로그아웃",
            message = "정말 로그아웃할까요?",
            confirmText = "로그아웃",
            dismissText = "취소",
            isDestructive = false,
        )
    }
}

@Preview
@Composable
private fun ConfirmationDialogDestructivePreview() {
    AppTheme {
        ConfirmationDialog(
            onDismissRequest = {},
            onConfirm = {},
            title = "계정 삭제",
            message = "삭제하면 되돌릴 수 없어요. 정말 삭제할까요?",
            confirmText = "삭제",
            dismissText = "취소",
            isDestructive = true,
        )
    }
}
