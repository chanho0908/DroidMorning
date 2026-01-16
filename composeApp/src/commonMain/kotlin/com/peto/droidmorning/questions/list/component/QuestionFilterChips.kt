package com.peto.droidmorning.questions.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.peto.droidmorning.designsystem.component.CategoryFilterChip
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.Category
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.question_filter_category
import droidmorning.composeapp.generated.resources.question_filter_favorites
import droidmorning.composeapp.generated.resources.question_filter_solved
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestionFilterChips(
    selectedCategories: ImmutableSet<Category>,
    showSolvedOnly: Boolean,
    showLikedOnly: Boolean,
    onToggleCategoryFilters: () -> Unit,
    onSolvedFilterToggle: () -> Unit,
    onLikedFilterToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
        modifier = modifier.fillMaxWidth(),
    ) {
        CategoryFilterChip(
            text = stringResource(Res.string.question_filter_category),
            selected = selectedCategories.isNotEmpty(),
            onClick = onToggleCategoryFilters,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = null,
                )
            },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.question_filter_solved),
            selected = showSolvedOnly,
            onClick = onSolvedFilterToggle,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                )
            },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.question_filter_favorites),
            selected = showLikedOnly,
            onClick = onLikedFilterToggle,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                )
            },
        )
    }
}

@Preview
@Composable
private fun QuestionFilterChipsPreview() {
    AppTheme {
        QuestionFilterChips(
            selectedCategories = persistentSetOf(),
            showSolvedOnly = false,
            showLikedOnly = false,
            onToggleCategoryFilters = {},
            onSolvedFilterToggle = {},
            onLikedFilterToggle = {},
        )
    }
}

@Preview
@Composable
private fun QuestionFilterChipsSelectedPreview() {
    AppTheme {
        QuestionFilterChips(
            selectedCategories = persistentSetOf(Category.Kotlin, Category.Android),
            showSolvedOnly = true,
            showLikedOnly = true,
            onToggleCategoryFilters = {},
            onSolvedFilterToggle = {},
            onLikedFilterToggle = {},
        )
    }
}
