package com.peto.droidmorning.exam.progress.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.ExamSelected
import com.peto.droidmorning.designsystem.theme.ExamUnSelected
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.exam_answer_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExamAnswerInput(
    answer: String,
    onAnswerChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor =
        if (isFocused) {
            ExamSelected
        } else {
            ExamUnSelected
        }

    TextField(
        value = answer,
        onValueChange = onAnswerChange,
        modifier =
            modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(24.dp),
                ),
        placeholder = {
            Text(
                text = stringResource(Res.string.exam_answer_placeholder),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            )
        },
        shape = RoundedCornerShape(24.dp),
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        textStyle = MaterialTheme.typography.bodyLarge,
        interactionSource = interactionSource,
    )
}

@Preview
@Composable
private fun ExamAnswerInputPreview() {
    AppTheme {
        ExamAnswerInput(
            answer = "data class는 자동으로 equals, hashCode, toString 메서드를 생성합니다.",
            onAnswerChange = {},
        )
    }
}

@Preview
@Composable
private fun ExamAnswerInputEmptyPreview() {
    AppTheme {
        ExamAnswerInput(
            answer = "",
            onAnswerChange = {},
        )
    }
}
