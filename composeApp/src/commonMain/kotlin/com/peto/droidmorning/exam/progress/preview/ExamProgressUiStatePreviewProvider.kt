package com.peto.droidmorning.exam.progress.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamQuestion
import com.peto.droidmorning.domain.model.exam.Exams
import com.peto.droidmorning.exam.progress.model.ExamProgressUiState

class ExamProgressUiStatePreviewProvider : PreviewParameterProvider<ExamProgressUiState> {
    override val values: Sequence<ExamProgressUiState>
        get() =
            sequenceOf(
                ExamProgressUiState(
                    questions =
                        listOf(
                            ExamQuestion(
                                questionId = 1,
                                content = "Kotlin의 data class와 일반 class의 차이점은?",
                                category = Category.Kotlin,
                            ),
                            ExamQuestion(
                                questionId = 2,
                                content = "Coroutine의 Dispatcher 종류를 설명하시오",
                                category = Category.Coroutine,
                            ),
                            ExamQuestion(
                                questionId = 3,
                                content = "안드로이드 4대 컴포넌트에 대해 설명해주세요.",
                                category = Category.Android,
                            ),
                            ExamQuestion(
                                questionId = 4,
                                content = "선언형 UI란 무엇인가요 ?",
                                category = Category.Compose,
                            ),
                            ExamQuestion(
                                questionId = 5,
                                content = "단일 책임 원칙에 대해 설명해주세요",
                                category = Category.OOP,
                            ),
                        ),
                    currentQuestionIndex = 0,
                    exams = Exams(),
                    isLoading = false,
                ),
            )
}
