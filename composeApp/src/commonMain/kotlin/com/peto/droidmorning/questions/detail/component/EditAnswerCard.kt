package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.component.AppPrimaryButton
import com.peto.droidmorning.designsystem.component.AppTextArea
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.cancel
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.answer_placeholder
import droidmorning.composeapp.generated.resources.save
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditAnswerCard(
    content: String,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(Dimen.spacingBase),
            verticalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
        ) {
            AppTextArea(
                value = content,
                onValueChange = onContentChange,
                placeholder = stringResource(Res.string.answer_placeholder),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(Dimen.spacing5xl * 2),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = onCancel) {
                    Text(
                        stringResource(DesignRes.string.cancel),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.width(Dimen.spacingSm))

                AppPrimaryButton(
                    text = stringResource(Res.string.save),
                    onClick = onSave,
                    enabled = content.isNotEmpty(),
                    modifier = Modifier.width(80.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun EditAnswerCardPreview() {
    var content by remember { mutableStateOf("") }
    AppTheme {
        EditAnswerCard(
            content = content,
            onContentChange = { },
            onSave = {},
            onCancel = {},
        )
    }
}

@Preview
@Composable
private fun EditAnswerCardWithContentPreview() {
    AppTheme {
        EditAnswerCard(
            content = "lateinit은 var 프로퍼티에만 사용 가능하며, 나중에 초기화할 수 있습니다.",
            onContentChange = { },
            onSave = {},
            onCancel = {},
        )
    }
}
