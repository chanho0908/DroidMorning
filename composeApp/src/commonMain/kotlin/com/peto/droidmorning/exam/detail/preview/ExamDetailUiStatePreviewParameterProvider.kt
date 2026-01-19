package com.peto.droidmorning.exam.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import com.peto.droidmorning.exam.detail.model.ExamDetailUiState

class ExamDetailUiStatePreviewParameterProvider : PreviewParameterProvider<ExamDetailUiState> {
    override val values: Sequence<ExamDetailUiState> =
        sequenceOf(
            ExamDetailUiState(
                examQuestions =
                    listOf(
                        ExamDetail(
                            examItemId = 1L,
                            examId = 1L,
                            questionId = 1L,
                            userAnswer = "val은 불변(immutable) 변수이고, var는 가변(mutable) 변수입니다.",
                            questionTitle = "Kotlin의 val과 var의 차이점은 무엇인가요?",
                            questionCategory = Category.Kotlin,
                            questionSourceUrl = "https://kotlinlang.org/docs/basic-syntax.html",
                        ),
                    ),
            ),
            ExamDetailUiState(
                examQuestions =
                    listOf(
                        ExamDetail(
                            examItemId = 1L,
                            examId = 1L,
                            questionId = 1L,
                            userAnswer = "val은 불변(immutable) 변수이고, var는 가변(mutable) 변수입니다.",
                            questionTitle = "Kotlin의 val과 var의 차이점은 무엇인가요?",
                            questionCategory = Category.Kotlin,
                            questionSourceUrl = "https://kotlinlang.org/docs/basic-syntax.html",
                        ),
                        ExamDetail(
                            examItemId = 2L,
                            examId = 1L,
                            questionId = 2L,
                            userAnswer = "Dispatchers.Main, Dispatchers.IO, Dispatchers.Default, Dispatchers.Unconfined가 있습니다.",
                            questionTitle = "Coroutine의 Dispatcher 종류에 대해 설명해주세요.",
                            questionCategory = Category.Coroutine,
                            questionSourceUrl = "https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html",
                        ),
                        ExamDetail(
                            examItemId = 3L,
                            examId = 1L,
                            questionId = 3L,
                            userAnswer = "Composable 함수는 UI를 선언적으로 구성하는 함수입니다.",
                            questionTitle = "Jetpack Compose의 Composable 함수에 대해 설명해주세요.",
                            questionCategory = Category.Compose,
                            questionSourceUrl = "https://developer.android.com/jetpack/compose",
                        ),
                        ExamDetail(
                            examItemId = 4L,
                            examId = 1L,
                            questionId = 4L,
                            userAnswer = "ViewModel은 UI 관련 데이터를 보관하고 관리하는 클래스입니다.",
                            questionTitle = "Android의 ViewModel에 대해 설명해주세요.",
                            questionCategory = Category.Android,
                            questionSourceUrl = "https://developer.android.com/topic/libraries/architecture/viewmodel",
                        ),
                        ExamDetail(
                            examItemId = 5L,
                            examId = 1L,
                            questionId = 5L,
                            userAnswer = "SOLID 원칙은 객체지향 프로그래밍의 5가지 설계 원칙입니다.",
                            questionTitle = "SOLID 원칙에 대해 설명해주세요.",
                            questionCategory = Category.OOP,
                            questionSourceUrl = "https://en.wikipedia.org/wiki/SOLID",
                        ),
                    ),
            ),
            ExamDetailUiState(
                examQuestions = emptyList(),
            ),
        )
}
