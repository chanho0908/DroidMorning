package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import com.peto.droidmorning.questions.detail.preview.AnswerCardPreviewParameterProvider
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.delete
import droidmorning.composeapp.generated.resources.edit
import droidmorning.composeapp.generated.resources.last_modified_prefix
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnswerCard(
    answer: AnswerUiModel,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
) {
    val displayDate =
        when (answer) {
            is AnswerUiModel.Current -> answer.updatedDate
            is AnswerUiModel.History -> answer.createdDate
        }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = Dimen.spacingBase)
                    .padding(top = Dimen.spacingLg, bottom = Dimen.spacingSm),
            verticalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
        ) {
            Text(
                text = answer.content,
                style = MaterialTheme.typography.bodyLarge,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (answer is AnswerUiModel.Current) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXxs)) {
                        Text(
                            text = stringResource(Res.string.last_modified_prefix),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = displayDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    Text(
                        text = displayDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                if (onEdit != null || onDelete != null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXxs)) {
                        onEdit?.let {
                            IconButton(onClick = onEdit) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = stringResource(Res.string.edit),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(Dimen.iconSm),
                                )
                            }
                        }

                        onDelete?.let {
                            IconButton(onClick = onDelete) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = stringResource(Res.string.delete),
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(Dimen.iconSm),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AnswerCardPreview(
    @PreviewParameter(AnswerCardPreviewParameterProvider::class)
    answer: AnswerUiModel,
) {
    AppTheme {
        AnswerCard(
            answer = answer,
            onEdit = {},
            onDelete = {},
        )
    }
}
