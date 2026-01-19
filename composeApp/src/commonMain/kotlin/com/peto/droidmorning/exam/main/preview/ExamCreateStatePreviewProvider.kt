package com.peto.droidmorning.exam.main.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.main.model.ExamCreateState

class ExamCreateStatePreviewProvider : PreviewParameterProvider<ExamCreateState> {
    override val values: Sequence<ExamCreateState>
        get() =
            sequenceOf(
                ExamCreateState(),
                ExamCreateState(
                    selectedQuestionCount = 10,
                    selectedCategories = listOf(Category.Kotlin, Category.Android),
                    categoryCountMap =
                        mapOf(
                            Category.Kotlin to 50L,
                            Category.Android to 30L,
                            Category.Compose to 20L,
                            Category.Coroutine to 15L,
                            Category.OOP to 10L,
                        ),
                ),
                ExamCreateState(
                    selectedQuestionCount = 5,
                    selectedCategories = listOf(Category.Kotlin),
                    categoryCountMap =
                        mapOf(
                            Category.Kotlin to 100L,
                            Category.Android to 50L,
                        ),
                ),
            )
}
