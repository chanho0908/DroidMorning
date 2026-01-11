package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .heightIn(min = Dimen.touchTargetMin)
                .semantics {
                    contentDescription = "$label ${if (checked) "선택됨" else "선택 안됨"}"
                },
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors =
                CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                ),
        )
        Spacer(modifier = Modifier.width(Dimen.spacingSm))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color =
                if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
        )
    }
}

@Preview
@Composable
private fun AppCheckboxUncheckedPreview() {
    AppTheme {
        AppCheckbox(
            checked = false,
            onCheckedChange = {},
            label = "약관에 동의합니다",
        )
    }
}

@Preview
@Composable
private fun AppCheckboxCheckedPreview() {
    AppTheme {
        AppCheckbox(
            checked = true,
            onCheckedChange = {},
            label = "약관에 동의합니다",
        )
    }
}
