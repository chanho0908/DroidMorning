package com.peto.droidmorning.exam.progress.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
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
import droidmorning.composeapp.generated.resources.exam_button_next
import droidmorning.composeapp.generated.resources.exam_button_previous
import droidmorning.composeapp.generated.resources.exam_button_submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExamNavigationButtons(
    isFirstQuestion: Boolean,
    isLastQuestion: Boolean,
    canGoNext: Boolean,
    canSubmit: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PreviousButton(
            isFirstQuestion = isFirstQuestion,
            onPreviousClick = onPreviousClick,
            modifier = Modifier.weight(1f),
        )

        NextButton(
            isLastQuestion = isLastQuestion,
            canGoNext = canGoNext,
            canSubmit = canSubmit,
            onNextClick = onNextClick,
            onSubmitClick = onSubmitClick,
            modifier = Modifier.weight(2f),
        )
    }
}

@Composable
private fun PreviousButton(
    isFirstQuestion: Boolean,
    onPreviousClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isFirstQuestion) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ).border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp),
                ).clickable(enabled = !isFirstQuestion) {
                    onPreviousClick()
                }.padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint =
                    if (isFirstQuestion) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
            Text(
                text = stringResource(Res.string.exam_button_previous),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color =
                    if (isFirstQuestion) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
            )
        }
    }
}

@Composable
private fun NextButton(
    isLastQuestion: Boolean,
    canGoNext: Boolean,
    canSubmit: Boolean,
    onNextClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEnabled = if (isLastQuestion) canSubmit else canGoNext

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ).clickable(enabled = isEnabled) {
                    if (isLastQuestion) {
                        onSubmitClick()
                    } else {
                        onNextClick()
                    }
                }.padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text =
                    if (isLastQuestion) {
                        stringResource(Res.string.exam_button_submit)
                    } else {
                        stringResource(Res.string.exam_button_next)
                    },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isEnabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Icon(
                imageVector =
                    if (isLastQuestion) {
                        Icons.Default.Send
                    } else {
                        Icons.AutoMirrored.Filled.ArrowForward
                    },
                contentDescription = null,
                tint = if (isEnabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun ExamNavigationButtonsFirstQuestionPreview() {
    AppTheme {
        ExamNavigationButtons(
            isFirstQuestion = true,
            isLastQuestion = false,
            canGoNext = true,
            canSubmit = true,
            onPreviousClick = {},
            onNextClick = {},
            onSubmitClick = {},
        )
    }
}

@Preview
@Composable
private fun ExamNavigationButtonsMiddleQuestionPreview() {
    AppTheme {
        ExamNavigationButtons(
            isFirstQuestion = false,
            isLastQuestion = false,
            canGoNext = true,
            canSubmit = true,
            onPreviousClick = {},
            onNextClick = {},
            onSubmitClick = {},
        )
    }
}

@Preview
@Composable
private fun ExamNavigationButtonsLastQuestionPreview() {
    AppTheme {
        ExamNavigationButtons(
            isFirstQuestion = false,
            isLastQuestion = true,
            canGoNext = true,
            canSubmit = true,
            onPreviousClick = {},
            onNextClick = {},
            onSubmitClick = {},
        )
    }
}

@Preview
@Composable
private fun ExamNavigationButtonsDisabledPreview() {
    AppTheme {
        ExamNavigationButtons(
            isFirstQuestion = false,
            isLastQuestion = false,
            canGoNext = false,
            canSubmit = false,
            onPreviousClick = {},
            onNextClick = {},
            onSubmitClick = {},
        )
    }
}
