package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.peto.droidmorning.designsystem.component.ConfirmDialog
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.preview.AnswerUiModelPreviewParameterProvider
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.cancel
import droidmorning.composeapp.generated.resources.delete
import droidmorning.composeapp.generated.resources.delete_answer_confirm_message
import droidmorning.composeapp.generated.resources.delete_answer_title
import droidmorning.composeapp.generated.resources.my_answer
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyAnswer(
    answer: AnswerUiModel.Current?,
    onUpdateAnswer: (AnswerUiModel.Current, String) -> Unit,
    onDeleteAnswer: (AnswerUiModel.Current) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isEditing by remember { mutableStateOf(false) }
    var editContent by remember { mutableStateOf("") }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimen.iconMd),
            )
            Text(
                text = stringResource(Res.string.my_answer),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        when (answer) {
            null -> EmptyAnswerCard()
            else -> {
                when {
                    isEditing -> {
                        EditAnswerCard(
                            content = editContent,
                            onContentChange = { editContent = it },
                            onSave = {
                                if (editContent.trim().isNotEmpty()) {
                                    onUpdateAnswer(answer, editContent)
                                    isEditing = false
                                }
                            },
                            onCancel = {
                                isEditing = false
                                editContent = ""
                            },
                        )
                    }

                    else -> {
                        AnswerCard(
                            answer = answer,
                            onEdit = {
                                isEditing = true
                                editContent = answer.content
                            },
                            onDelete = { showDeleteConfirm = true },
                        )
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
                        confirmText = stringResource(Res.string.delete),
                        cancelText = stringResource(Res.string.cancel),
                        icon = Icons.Outlined.Delete,
                        iconTint = MaterialTheme.colorScheme.error,
                        iconBackgroundColor = MaterialTheme.colorScheme.errorContainer,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyAnswerPreview(
    @PreviewParameter(AnswerUiModelPreviewParameterProvider::class)
    answer: AnswerUiModel.Current,
) {
    AppTheme {
        MyAnswer(
            answer = answer,
            onUpdateAnswer = { _, _ -> },
            onDeleteAnswer = {},
        )
    }
}
