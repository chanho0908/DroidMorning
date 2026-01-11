package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.CategoryAndroid
import com.peto.droidmorning.designsystem.theme.CategoryCompose
import com.peto.droidmorning.designsystem.theme.CategoryCoroutine
import com.peto.droidmorning.designsystem.theme.CategoryKotlin
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryBadge(
    category: String,
    categoryColor: Color,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = categoryColor.copy(alpha = 0.15f)

    Surface(
        modifier = modifier,
        shape = Shape.categoryBadge,
        color = backgroundColor,
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelMedium,
            color = categoryColor,
            modifier =
                Modifier.padding(
                    horizontal = Dimen.badgePaddingHorizontal,
                    vertical = Dimen.badgePaddingVertical,
                ),
        )
    }
}

@Preview
@Composable
private fun CategoryBadgePreview() {
    AppTheme {
        Column {
            CategoryBadge(
                categoryColor = CategoryKotlin,
                category = "Android",
            )
            CategoryBadge(
                categoryColor = CategoryCompose,
                category = "Kotlin",
            )
            CategoryBadge(
                categoryColor = CategoryCoroutine,
                category = "Compose",
            )
            CategoryBadge(
                categoryColor = CategoryAndroid,
                category = "Coroutine",
            )
        }
    }
}
