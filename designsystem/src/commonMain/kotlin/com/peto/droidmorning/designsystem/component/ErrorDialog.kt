package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.error_dialog_default_button
import com.peto.droidmorning.designsystem.generated.resources.error_dialog_default_title
import com.peto.droidmorning.designsystem.generated.resources.error_dialog_icon_description
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String = stringResource(DesignRes.string.error_dialog_default_title),
    message: String,
    buttonText: String = stringResource(DesignRes.string.error_dialog_default_button),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        ErrorDialogContent(
            title = title,
            message = message,
            buttonText = buttonText,
            onDismiss = onDismissRequest,
        )
    }
}

@Composable
private fun ErrorDialogContent(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    buttonText: String,
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
                        .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = stringResource(DesignRes.string.error_dialog_icon_description),
                    tint = MaterialTheme.colorScheme.error,
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

            AppPrimaryButton(
                text = buttonText,
                onClick = onDismiss,
            )
        }
    }
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    AppTheme {
        ErrorDialogContent(
            onDismiss = {},
            title = "오류 발생",
            message = "This is a sample error message.",
            buttonText = "확인",
        )
    }
}
