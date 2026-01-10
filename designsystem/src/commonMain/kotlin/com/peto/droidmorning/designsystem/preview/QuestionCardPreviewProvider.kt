package com.peto.droidmorning.designsystem.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class QuestionCardPreviewState(
    val title: String,
    val category: String,
    val isSolved: Boolean,
    val isFavorite: Boolean,
)

class QuestionCardPreviewProvider : PreviewParameterProvider<QuestionCardPreviewState> {
    override val values =
        sequenceOf(
            QuestionCardPreviewState(
                title = "스트림이란 무엇인가요 ?",
                category = "kotlin",
                isSolved = true,
                isFavorite = true,
            ),
            QuestionCardPreviewState(
                title = "ANR이란 무엇인지, ANR이 발생하는 주요 원인은 무엇이며, 어떻게 예방할 수 있는지 설명해주세요.",
                category = "android",
                isSolved = true,
                isFavorite = false,
            ),
            QuestionCardPreviewState(
                title = "Dispatchers.Main / IO / Default 용도 와 차이점에 대해서 설명해주세요.",
                category = "coroutine",
                isSolved = false,
                isFavorite = true,
            ),
            QuestionCardPreviewState(
                title = "선언형UI란 무엇인가요 ?",
                category = "compose",
                isSolved = false,
                isFavorite = false,
            ),
        )
}
