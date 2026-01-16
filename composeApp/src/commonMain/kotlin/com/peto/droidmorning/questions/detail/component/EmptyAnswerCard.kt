package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.no_answer_yet
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyAnswerCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Dimen.spacing2xl),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.no_answer_yet),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyAnswerCardPreview() {
    AppTheme {
        EmptyAnswerCard()
    }
}
