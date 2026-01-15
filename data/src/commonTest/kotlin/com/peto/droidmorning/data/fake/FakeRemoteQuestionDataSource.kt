package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.data.model.QuestionResponse

class FakeRemoteQuestionDataSource(
    private val questions: List<QuestionResponse>,
) : RemoteQuestionDataSource {
    private val likedQuestions = mutableSetOf<Long>()

    override suspend fun fetchQuestions(): List<QuestionResponse> = questions

    override suspend fun addLike(questionId: Long) {
        likedQuestions.add(questionId)
    }

    override suspend fun removeLike(questionId: Long) {
        likedQuestions.remove(questionId)
    }

    fun isLiked(questionId: Long): Boolean = likedQuestions.contains(questionId)

    fun clearLikes() {
        likedQuestions.clear()
    }
}
