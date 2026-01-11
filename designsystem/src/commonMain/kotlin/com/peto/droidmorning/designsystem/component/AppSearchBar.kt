package com.peto.droidmorning.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Border
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.MutedForeground
import com.peto.droidmorning.designsystem.theme.OnSurface
import com.peto.droidmorning.designsystem.theme.Primary
import com.peto.droidmorning.designsystem.theme.Secondary
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "검색...",
    enabled: Boolean = true,
    onClear: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Primary else Border,
        label = "border_color",
    )

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier =
            modifier
                .fillMaxWidth()
                .height(Dimen.inputHeight)
                .clip(Shape.buttonPill)
                .background(Secondary)
                .border(1.dp, borderColor, Shape.buttonPill)
                .padding(horizontal = Dimen.spacingMd),
        enabled = enabled,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = OnSurface),
        cursorBrush = SolidColor(Primary),
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = { onSearch(query) },
            ),
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MutedForeground,
                    modifier = Modifier.size(Dimen.iconSm),
                )
                Spacer(modifier = Modifier.width(Dimen.spacingSm))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MutedForeground,
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            onClear?.invoke()
                        },
                        modifier = Modifier.size(Dimen.touchTargetMin),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "검색어 지우기",
                            tint = MutedForeground,
                            modifier = Modifier.size(Dimen.iconSm),
                        )
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun AppSearchBarPreview() {
    AppTheme {
        AppSearchBar(
            query = "Compose",
            onQueryChange = {},
            onSearch = {},
        )
    }
}

@Preview
@Composable
private fun AppSearchBarEmptyPreview() {
    AppTheme {
        AppSearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
        )
    }
}
