package com.peto.droidmorning.questions.detail.model

import com.peto.droidmorning.common.util.DateFormatter
import com.peto.droidmorning.domain.model.Answer

sealed class AnswerUiModel {
    abstract val questionId: Long
    abstract val content: String
    abstract val createdDate: String
    abstract val updatedDate: String

    data class Current(
        override val questionId: Long,
        override val content: String,
        override val createdDate: String,
        override val updatedDate: String,
    ) : AnswerUiModel()

    data class History(
        val id: Long,
        override val questionId: Long,
        override val content: String,
        override val createdDate: String,
    ) : AnswerUiModel() {
        override val updatedDate: String
            get() = createdDate
    }
}

fun Answer.toUiModel(): AnswerUiModel =
    when (this) {
        is Answer.Current ->
            AnswerUiModel.Current(
                questionId = questionId,
                content = content,
                createdDate = DateFormatter.formatDate(createdAt),
                updatedDate = DateFormatter.formatDate(updatedAt),
            )

        is Answer.History ->
            AnswerUiModel.History(
                id = id,
                questionId = questionId,
                content = content,
                createdDate = DateFormatter.formatDate(createdAt),
            )
    }
