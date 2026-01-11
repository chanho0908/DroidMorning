package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
fun AppRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
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
                    contentDescription = "$label ${if (selected) "선택됨" else "선택 안됨"}"
                },
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            colors =
                RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
private fun AppRadioButtonUnselectedPreview() {
    AppTheme {
        AppRadioButton(
            selected = false,
            onClick = {},
            label = "옵션 A",
        )
    }
}

@Preview
@Composable
private fun AppRadioButtonSelectedPreview() {
    AppTheme {
        AppRadioButton(
            selected = true,
            onClick = {},
            label = "옵션 A",
        )
    }
}
