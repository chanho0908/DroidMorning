package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.cancel
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.add_answer_placeholder
import droidmorning.composeapp.generated.resources.add_answer_title
import droidmorning.composeapp.generated.resources.save
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnswerBottomSheet(
    draftAnswer: String,
    onDraftAnswerChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
) {
    AddAnswerBottomSheetContent(
        draftAnswer = draftAnswer,
        onDraftAnswerChange = onDraftAnswerChange,
        onDismiss = onDismiss,
        onSave = onSave,
        modifier = modifier,
        sheetState = sheetState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAnswerBottomSheetContent(
    draftAnswer: String,
    onDraftAnswerChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = Dimen.spacingLg)
                    .padding(bottom = Dimen.spacingLg),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.add_answer_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                Row(horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXs)) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        },
                    ) {
                        Text(stringResource(DesignRes.string.cancel))
                    }

                    TextButton(
                        onClick = {
                            if (draftAnswer.isNotEmpty()) {
                                scope.launch {
                                    onSave(draftAnswer)
                                    sheetState.hide()
                                    onDismiss()
                                }
                            }
                        },
                        enabled = draftAnswer.trim().isNotEmpty(),
                    ) {
                        Text(stringResource(Res.string.save))
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimen.spacingMd))

            TextField(
                value = draftAnswer,
                onValueChange = onDraftAnswerChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimen.textFieldHeightLarge),
                placeholder = {
                    Text(
                        text = stringResource(Res.string.add_answer_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                keyboardOptions =
                    KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Default,
                    ),
                shape = MaterialTheme.shapes.medium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AddAnswerBottomSheetPreview() {
    AppTheme {
        AddAnswerBottomSheetContent(
            draftAnswer = "",
            onDraftAnswerChange = {},
            onDismiss = {},
            onSave = {},
        )
    }
}
