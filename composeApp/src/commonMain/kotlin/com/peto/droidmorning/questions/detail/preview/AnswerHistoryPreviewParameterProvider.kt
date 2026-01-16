package com.peto.droidmorning.questions.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.questions.detail.model.AnswerUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class AnswerHistoryPreviewParameterProvider : PreviewParameterProvider<ImmutableList<AnswerUiModel.History>> {
    override val values: Sequence<ImmutableList<AnswerUiModel.History>> =
        sequenceOf(
            persistentListOf(
                AnswerUiModel.History(
                    id = 1L,
                    questionId = 1L,
                    content = "lateinit은 var 프로퍼티에만 사용 가능하며, 나중에 초기화할 수 있습니다.",
                    createdDate = "2024.01.10",
                ),
            ),
            persistentListOf(
                AnswerUiModel.History(
                    id = 1L,
                    questionId = 1L,
                    content = "lateinit은 나중에 초기화할 수 있습니다.",
                    createdDate = "2024.01.10",
                ),
                AnswerUiModel.History(
                    id = 2L,
                    questionId = 1L,
                    content = "lateinit은 var 프로퍼티에만 사용 가능하고, lazy는 val 프로퍼티에 사용됩니다.",
                    createdDate = "2024.01.12",
                ),
                AnswerUiModel.History(
                    id = 3L,
                    questionId = 1L,
                    content =
                        """
                        lateinit과 lazy의 주요 차이점:
                        
                        1. lateinit은 var 프로퍼티에만 사용 가능
                        2. lazy는 val 프로퍼티에 사용
                        3. lateinit은 나중에 초기화 가능
                        4. lazy는 처음 접근할 때 자동 초기화
                        """.trimIndent(),
                    createdDate = "2024.01.15",
                ),
            ),
            persistentListOf(
                AnswerUiModel.History(
                    id = 1L,
                    questionId = 1L,
                    content = "첫 번째 시도입니다.",
                    createdDate = "2024.01.01",
                ),
                AnswerUiModel.History(
                    id = 2L,
                    questionId = 1L,
                    content = "두 번째 시도: lateinit 추가",
                    createdDate = "2024.01.05",
                ),
                AnswerUiModel.History(
                    id = 3L,
                    questionId = 1L,
                    content = "세 번째 시도: lazy 추가",
                    createdDate = "2024.01.10",
                ),
                AnswerUiModel.History(
                    id = 4L,
                    questionId = 1L,
                    content = "네 번째 시도: 차이점 비교 추가",
                    createdDate = "2024.01.12",
                ),
                AnswerUiModel.History(
                    id = 5L,
                    questionId = 1L,
                    content = "다섯 번째 시도: 예제 코드 추가",
                    createdDate = "2024.01.15",
                ),
            ),
        )
}
