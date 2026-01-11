package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.peto.droidmorning.designsystem.preview.QuestionCardPreviewProvider
import com.peto.droidmorning.designsystem.preview.QuestionCardPreviewState
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.CategoryKotlin
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.MutedForeground
import com.peto.droidmorning.designsystem.theme.OnSurface
import com.peto.droidmorning.designsystem.theme.Success
import com.peto.droidmorning.designsystem.theme.Warning
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun QuestionCard(
    title: String,
    category: String,
    categoryColor: Color,
    isSolved: Boolean,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InteractiveCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CategoryBadge(
                        category = category,
                        categoryColor = categoryColor,
                    )

                    if (isSolved) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "풀이 완료",
                            modifier = Modifier.size(Dimen.iconXs),
                            tint = Success,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimen.spacingSm))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            IconButton(
                onClick = onFavoriteClick,
                content = {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = if (isFavorite) "즐겨찾기 해제" else "즐겨찾기 추가",
                        tint = if (isFavorite) Warning else MutedForeground,
                        modifier = Modifier.size(Dimen.iconSm),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun QuestionCardPreview(
    @PreviewParameter(QuestionCardPreviewProvider::class)
    state: QuestionCardPreviewState,
) {
    AppTheme {
        QuestionCard(
            title = state.title,
            category = state.category,
            isSolved = state.isSolved,
            isFavorite = state.isFavorite,
            categoryColor = CategoryKotlin,
            onClick = {},
            onFavoriteClick = {},
        )
    }
}
