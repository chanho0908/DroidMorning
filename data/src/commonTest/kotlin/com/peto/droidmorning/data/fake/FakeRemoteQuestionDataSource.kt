package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.data.model.response.CategoryCountResponse
import com.peto.droidmorning.data.model.response.ExamQuestionResponse
import com.peto.droidmorning.data.model.response.QuestionResponse

class FakeRemoteQuestionDataSource(
    private val questions: List<QuestionResponse> = emptyList(),
    private val examQuestions: List<ExamQuestionResponse> = emptyList(),
    private val categoryCount: List<CategoryCountResponse> = emptyList(),
) : RemoteQuestionDataSource {
    private val likedQuestions = mutableSetOf<Long>()

    override suspend fun fetchExamQuestions(): List<QuestionResponse> = questions

    override suspend fun fetchExamQuestions(
        category: List<String>,
        count: Int,
    ): List<ExamQuestionResponse> =
        if (category.isEmpty()) {
            examQuestions.take(count)
        } else {
            examQuestions
                .filter { it.category in category }
                .take(count)
        }

    override suspend fun addLike(questionId: Long) {
        likedQuestions.add(questionId)
    }

    override suspend fun removeLike(questionId: Long) {
        likedQuestions.remove(questionId)
    }

    override suspend fun fetchCategoryCount(): List<CategoryCountResponse> = categoryCount

    fun isLiked(questionId: Long): Boolean = likedQuestions.contains(questionId)

    fun clearLikes() {
        likedQuestions.clear()
    }
}
