package com.peto.droidmorning.question.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.peto.droidmorning.designsystem.component.CategoryFilterChip
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.domain.model.Category
import droidmorning.composeapp.generated.resources.Res
import droidmorning.composeapp.generated.resources.category_android
import droidmorning.composeapp.generated.resources.category_compose
import droidmorning.composeapp.generated.resources.category_coroutine
import droidmorning.composeapp.generated.resources.category_kotlin
import droidmorning.composeapp.generated.resources.category_oop
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryChips(
    selectedCategories: ImmutableSet<Category>,
    onCategoryToggle: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimen.spacingSm),
        modifier =
            modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
    ) {
        CategoryFilterChip(
            text = stringResource(Res.string.category_kotlin),
            selected = selectedCategories.contains(Category.Kotlin),
            onClick = { onCategoryToggle(Category.Kotlin) },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.category_android),
            selected = selectedCategories.contains(Category.Android),
            onClick = { onCategoryToggle(Category.Android) },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.category_compose),
            selected = selectedCategories.contains(Category.Compose),
            onClick = { onCategoryToggle(Category.Compose) },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.category_coroutine),
            selected = selectedCategories.contains(Category.Coroutine),
            onClick = { onCategoryToggle(Category.Coroutine) },
        )

        CategoryFilterChip(
            text = stringResource(Res.string.category_oop),
            selected = selectedCategories.contains(Category.OOP),
            onClick = { onCategoryToggle(Category.OOP) },
        )
    }
}

@Preview
@Composable
private fun CategoryChipsPreview() {
    AppTheme {
        CategoryChips(
            selectedCategories = persistentSetOf(),
            onCategoryToggle = {},
        )
    }
}

@Preview
@Composable
private fun CategoryChipsSelectedPreview() {
    AppTheme {
        CategoryChips(
            selectedCategories = persistentSetOf(Category.Kotlin, Category.Android, Category.Compose),
            onCategoryToggle = {},
        )
    }
}
