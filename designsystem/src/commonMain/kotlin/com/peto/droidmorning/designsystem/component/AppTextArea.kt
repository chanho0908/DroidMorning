package com.peto.droidmorning.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "입력하세요",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxCharacters: Int? = null,
    keyboardOptions: KeyboardOptions =
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Default,
        ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue =
            when {
                isError -> MaterialTheme.colorScheme.error
                isFocused -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outline
            },
        label = "textarea_border_color",
    )

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (maxCharacters == null || newValue.length <= maxCharacters) {
                    onValueChange(newValue)
                }
            },
            enabled = enabled,
            singleLine = false,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(Shape.inputField)
                    .background(MaterialTheme.colorScheme.secondary)
                    .border(1.dp, borderColor, Shape.inputField)
                    .padding(horizontal = Dimen.spacingMd, vertical = Dimen.spacingSm),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    innerTextField()
                }
            },
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isError && !errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            if (maxCharacters != null) {
                Text(
                    text = "${value.length}/$maxCharacters",
                    style = MaterialTheme.typography.bodySmall,
                    color =
                        if (value.length >= maxCharacters) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppTextAreaPreview() {
    AppTheme {
        AppTextArea(
            value = "메모를 적어보세요.",
            onValueChange = {},
            maxCharacters = 100,
        )
    }
}

@Preview
@Composable
private fun AppTextAreaErrorPreview() {
    AppTheme {
        AppTextArea(
            value = "",
            onValueChange = {},
            isError = true,
            errorMessage = "필수 입력입니다.",
        )
    }
}
