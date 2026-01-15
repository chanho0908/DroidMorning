package com.peto.droidmorning.question.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.peto.droidmorning.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyQuestion(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun QuestionEmptyStatePreview() {
    AppTheme {
        EmptyQuestion(
            message = "검색 결과가 없습니다",
        )
    }
}

@Preview
@Composable
private fun QuestionEmptyStateNoQuestionsPreview() {
    AppTheme {
        EmptyQuestion(
            message = "질문이 없습니다",
        )
    }
}
