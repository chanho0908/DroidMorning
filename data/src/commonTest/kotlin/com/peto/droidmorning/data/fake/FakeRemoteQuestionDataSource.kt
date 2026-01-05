package com.peto.droidmorning.data.fake

import com.peto.droidmorning.data.datasource.question.remote.RemoteQuestionDataSource
import com.peto.droidmorning.data.model.QuestionResponse

class FakeRemoteQuestionDataSource(
    private val questions: List<QuestionResponse>,
) : RemoteQuestionDataSource {
    override suspend fun fetchQuestions(): List<QuestionResponse> = questions

    override suspend fun fetchQuestionsByCategory(category: String): List<QuestionResponse> = questions.filter { it.category == category }

    override suspend fun searchQuestions(query: String): List<QuestionResponse> = questions.filter { it.title.contains(query) }
}
