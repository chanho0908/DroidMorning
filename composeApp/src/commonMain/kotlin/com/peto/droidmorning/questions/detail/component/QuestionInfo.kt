package com.peto.droidmorning.questions.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.component.CategoryBadge
import com.peto.droidmorning.designsystem.extension.color
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.Category
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.answer_completed
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestionInfo(
    title: String,
    category: Category,
    isSolved: Boolean,
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
            CategoryBadge(
                category = category,
                categoryColor = category.color,
            )

            if (isSolved) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Row(
                        modifier =
                            Modifier.padding(
                                horizontal = Dimen.badgePaddingHorizontal,
                                vertical = Dimen.badgePaddingVertical,
                            ),
                        horizontalArrangement = Arrangement.spacedBy(Dimen.spacingXxs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(Dimen.iconXs),
                        )
                        Text(
                            text = stringResource(Res.string.answer_completed),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionInfoSolvedPreview() {
    AppTheme {
        QuestionInfo(
            title = "Coroutine의 Dispatcher 종류에 대해 설명해주세요.",
            category = Category.Coroutine,
            isSolved = true,
        )
    }
}
