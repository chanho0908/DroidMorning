package com.peto.droidmorning.questions.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.peto.droidmorning.questions.detail.model.AnswerUiModel

class AnswerUiModelPreviewParameterProvider : PreviewParameterProvider<AnswerUiModel.Current> {
    override val values: Sequence<AnswerUiModel.Current> =
        sequenceOf(
            AnswerUiModel.Current(
                questionId = 1L,
                content =
                    "lateinit은 var 프로퍼티에만 사용 가능하며, 나중에 초기화할 수 있습니다. " +
                        "반면 lazy는 val 프로퍼티에 사용되며, 처음 접근할 때 초기화됩니다.",
                createdDate = "2024.01.15",
                updatedDate = "2024.01.16",
            ),
            AnswerUiModel.Current(
                questionId = 2L,
                content =
                    """
                    Kotlin의 data class는 equals(), hashCode(), toString(), copy() 메서드를 자동으로 생성합니다.
                    
                    주요 사항:
                    1. data class는 최소 하나의 primary constructor 파라미터가 필요합니다.
                    2. primary constructor의 모든 파라미터는 val 또는 var로 선언되어야 합니다.
                    3. abstract, open, sealed, inner 클래스가 될 수 없습니다.
                    4. copy() 메서드를 통해 불변 객체의 일부 프로퍼티만 변경한 새로운 객체를 쉽게 생성할 수 있습니다.
                    """.trimIndent(),
                createdDate = "2024.01.10",
                updatedDate = "2024.01.12",
            ),
        )
}
