package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.Questions

interface QuestionRepository {
    suspend fun fetchQuestions(): Result<Questions>

    suspend fun toggleQuestionLike(
        questionId: Long,
        isCurrentlyLiked: Boolean,
    ): Result<Boolean>
}
