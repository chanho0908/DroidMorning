package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.peto.droidmorning.designsystem.component.ConfirmDialog
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.cancel
import com.peto.droidmorning.designsystem.generated.resources.remove
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.preview.AnswerHistoryPreviewParameterProvider
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.answer_history_count
import droidmorning.composeapp.generated.resources.delete_answer_confirm_message
import droidmorning.composeapp.generated.resources.delete_answer_title
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnswerHistory(
    historyAnswers: ImmutableList<AnswerUiModel.History>,
    onDeleteAnswer: (AnswerUiModel.History) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimen.iconMd),
            )
            Text(
                text = "${stringResource(Res.string.answer_history_count)}(${historyAnswers.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = Dimen.spacingBase),
        ) {
            historyAnswers.forEachIndexed { index, answer ->
                key(answer.id) {
                    HistoryItem(
                        answer = answer,
                        isLast = index == historyAnswers.size - 1,
                        onDeleteAnswer = onDeleteAnswer,
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(
    answer: AnswerUiModel.History,
    isLast: Boolean,
    onDeleteAnswer: (AnswerUiModel.History) -> Unit,
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    val borderColor = MaterialTheme.colorScheme.outline
    val dotSize = Dimen.spacingSm

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .drawBehind {
                    val dotSizePx = dotSize.toPx()
                    val centerX = dotSizePx / 2f
                    val lineStartY = dotSizePx / 2f
                    drawLine(
                        color = borderColor,
                        start = Offset(centerX, lineStartY),
                        end = Offset(centerX, size.height),
                        strokeWidth = 2f,
                    )
                },
    ) {
        Box(
            modifier =
                Modifier
                    .size(dotSize)
                    .background(
                        MaterialTheme.colorScheme.onSurfaceVariant,
                        MaterialTheme.shapes.extraSmall,
                    ).align(Alignment.TopStart),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = Dimen.spacingLg),
            verticalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
        ) {
            AnswerCard(
                answer = answer,
                onEdit = null,
                onDelete = { showDeleteConfirm = true },
            )

            if (!isLast) {
                Spacer(modifier = Modifier.height(Dimen.spacingMd))
            }
        }

        if (showDeleteConfirm) {
            ConfirmDialog(
                onDismissRequest = { showDeleteConfirm = false },
                onConfirm = {
                    onDeleteAnswer(answer)
                    showDeleteConfirm = false
                },
                title = stringResource(Res.string.delete_answer_title),
                message = stringResource(Res.string.delete_answer_confirm_message),
                confirmText = stringResource(DesignRes.string.remove),
                cancelText = stringResource(DesignRes.string.cancel),
                icon = Icons.Outlined.Delete,
                iconTint = MaterialTheme.colorScheme.error,
                iconBackgroundColor = MaterialTheme.colorScheme.errorContainer,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnswerHistoryPreview(
    @PreviewParameter(AnswerHistoryPreviewParameterProvider::class)
    historyAnswers: ImmutableList<AnswerUiModel.History>,
) {
    AppTheme {
        AnswerHistory(
            historyAnswers = historyAnswers,
            onDeleteAnswer = {},
        )
    }
}
