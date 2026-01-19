package com.peto.droidmorning.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category

data class QuestionCardPreviewState(
    val title: String,
    val category: Category,
    val isSolved: Boolean,
    val isFavorite: Boolean,
)

class QuestionCardPreviewProvider : PreviewParameterProvider<QuestionCardPreviewState> {
    override val values =
        sequenceOf(
            QuestionCardPreviewState(
                title = "스트림이란 무엇인가요 ?",
                category = Category.Kotlin,
                isSolved = true,
                isFavorite = true,
            ),
            QuestionCardPreviewState(
                title = "ANR이란 무엇인지, ANR이 발생하는 주요 원인은 무엇이며, 어떻게 예방할 수 있는지 설명해주세요.",
                category = Category.Android,
                isSolved = true,
                isFavorite = false,
            ),
            QuestionCardPreviewState(
                title = "Dispatchers.Main / IO / Default 용도 와 차이점에 대해서 설명해주세요.",
                category = Category.Coroutine,
                isSolved = false,
                isFavorite = true,
            ),
            QuestionCardPreviewState(
                title = "선언형UI란 무엇인가요 ?",
                category = Category.Compose,
                isSolved = false,
                isFavorite = false,
            ),
        )
}
