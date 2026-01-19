package com.peto.droidmorning.exam.progress.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.component.CategoryBadge
import com.peto.droidmorning.designsystem.extension.color
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.domain.model.category.Category

@Composable
fun ExamQuestionHeader(
    category: Category,
    questionContent: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CategoryBadge(
            category = category,
            categoryColor = category.color,
        )

        Text(
            text = questionContent,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
private fun ExamQuestionHeaderPreview() {
    AppTheme {
        ExamQuestionHeader(
            category = Category.Kotlin,
            questionContent = "Kotlin의 data class와 일반 class의 차이점은?",
        )
    }
}
