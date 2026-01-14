package com.peto.droidmorning.question.component

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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QuestionFilterChips(
    selectedCategories: ImmutableSet<Category>,
    showSolvedOnly: Boolean,
    showFavoritesOnly: Boolean,
    onToggleCategoryFilters: () -> Unit,
    onSolvedFilterToggle: () -> Unit,
    onFavoritesFilterToggle: () -> Unit,
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
            selected = showFavoritesOnly,
            onClick = onFavoritesFilterToggle,
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
            showFavoritesOnly = false,
            onToggleCategoryFilters = {},
            onSolvedFilterToggle = {},
            onFavoritesFilterToggle = {},
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
            showFavoritesOnly = true,
            onToggleCategoryFilters = {},
            onSolvedFilterToggle = {},
            onFavoritesFilterToggle = {},
        )
    }
}
