package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Border
import com.peto.droidmorning.designsystem.theme.OnPrimary
import com.peto.droidmorning.designsystem.theme.OnSecondary
import com.peto.droidmorning.designsystem.theme.Primary
import com.peto.droidmorning.designsystem.theme.Secondary
import com.peto.droidmorning.designsystem.theme.Shape

@Composable
fun CategoryFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        modifier = modifier.height(36.dp),
        leadingIcon = leadingIcon,
        shape = Shape.buttonSmall,
        colors =
            FilterChipDefaults.filterChipColors(
                containerColor = Secondary,
                labelColor = OnSecondary,
                selectedContainerColor = Primary,
                selectedLabelColor = OnPrimary,
            ),
        border =
            FilterChipDefaults.filterChipBorder(
                borderColor = Border,
                selectedBorderColor = Primary,
                borderWidth = 1.dp,
                selectedBorderWidth = 0.dp,
                enabled = true,
                selected = selected,
            ),
    )
}

@Preview
@Composable
private fun FilterChipPreview() {
    AppTheme {
        Row {
            CategoryFilterChip(
                text = "Filter",
                selected = false,
                onClick = {},
            )

            CategoryFilterChip(
                text = "Filter",
                selected = true,
                onClick = {},
            )
        }
    }
}
