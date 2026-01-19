package com.peto.droidmorning.exam.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.exam_question_unit
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestionCountChip(
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ).border(
                    width = 1.dp,
                    color =
                        if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                    shape = RoundedCornerShape(16.dp),
                ).clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color =
                    if (selected) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
            Text(
                text = stringResource(Res.string.exam_question_unit),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color =
                    if (selected) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
        }
    }
}

@Preview
@Composable
private fun QuestionCountChipPreview() {
    AppTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            QuestionCountChip(
                count = 5,
                selected = false,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            QuestionCountChip(
                count = 10,
                selected = true,
                onClick = {},
                modifier = Modifier.weight(1f),
            )
        }
    }
}
