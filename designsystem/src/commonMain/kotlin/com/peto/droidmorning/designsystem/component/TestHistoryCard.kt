package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.MutedForeground
import com.peto.droidmorning.designsystem.theme.OnSurface
import com.peto.droidmorning.designsystem.theme.Primary
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TestHistoryCard(
    date: String,
    questionCount: Int,
    score: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InteractiveCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleSmall,
                    color = OnSurface,
                )
                Spacer(modifier = Modifier.height(Dimen.spacingXs))
                Text(
                    text = "${questionCount}문제",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedForeground,
                )
            }

            Text(
                text = score,
                style = MaterialTheme.typography.titleMedium,
                color = Primary,
            )
        }
    }
}

@Preview
@Composable
private fun TestHistoryCardPreview() {
    AppTheme {
        TestHistoryCard(
            date = "2023년 11월 20일",
            questionCount = 10,
            score = "8/10",
            onClick = {},
        )
    }
}
