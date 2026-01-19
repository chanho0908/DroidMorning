package com.peto.droidmorning.exam.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.exam.main.model.ExamHistoryState
import com.peto.droidmorning.exam.main.preview.ExamHistoryStatePreviewProvider

@Composable
fun ExamHistoryTab(
    state: ExamHistoryState,
    onOpenExamHistory: (Long) -> Unit,
    onDeleteExam: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.histories.isEmpty() -> {
                EmptyHistoryState()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(state.histories) { history ->
                        ExamHistoryCard(
                            uiModel = history,
                            onClick = { onOpenExamHistory(history.id) },
                            onDeleteClick = { onDeleteExam(history.id) },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExamHistoryTabPreview(
    @PreviewParameter(ExamHistoryStatePreviewProvider::class)
    state: ExamHistoryState,
) {
    AppTheme {
        ExamHistoryTab(
            state = state,
            onOpenExamHistory = {},
            onDeleteExam = {},
        )
    }
}
