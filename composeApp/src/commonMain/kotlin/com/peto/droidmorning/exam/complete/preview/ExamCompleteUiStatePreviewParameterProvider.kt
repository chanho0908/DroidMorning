package com.peto.droidmorning.exam.complete.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import com.peto.droidmorning.exam.complete.model.ExamCompleteUiState
import kotlinx.collections.immutable.toImmutableList

class ExamCompleteUiStatePreviewParameterProvider : PreviewParameterProvider<ExamCompleteUiState> {
    override val values: Sequence<ExamCompleteUiState> =
        sequenceOf(
            ExamCompleteUiState(
                examDetails =
                    listOf(
                        ExamDetail(
                            examItemId = 1L,
                            examId = 1L,
                            questionId = 1L,
                            userAnswer = "ViewModel은 UI 관련 데이터를 관리하는 클래스입니다.",
                            questionTitle = "Android에서 ViewModel의 역할은 무엇인가요?",
                            questionCategory = Category.Android,
                            questionSourceUrl = "https://example.com/question1",
                        ),
                        ExamDetail(
                            examItemId = 2L,
                            examId = 1L,
                            questionId = 2L,
                            userAnswer = "Coroutine은 비동기 프로그래밍을 위한 경량 스레드입니다.",
                            questionTitle = "Kotlin Coroutine은 무엇인가요?",
                            questionCategory = Category.Kotlin,
                            questionSourceUrl = "https://example.com/question2",
                        ),
                        ExamDetail(
                            examItemId = 3L,
                            examId = 1L,
                            questionId = 3L,
                            userAnswer = "Jetpack Compose는 선언형 UI 프레임워크입니다.",
                            questionTitle = "Jetpack Compose의 특징은?",
                            questionCategory = Category.Compose,
                            questionSourceUrl = "https://example.com/question3",
                        ),
                        ExamDetail(
                            examItemId = 4L,
                            examId = 1L,
                            questionId = 4L,
                            userAnswer = "몰라용",
                            questionTitle = "단일 책임 원칙에 대해 설명해주세요",
                            questionCategory = Category.OOP,
                            questionSourceUrl = "https://example.com/question4",
                        ),
                    ).toImmutableList(),
            ),
        )
}
