package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Question

interface QuestionRepository {
    suspend fun fetchQuestions(): Result<List<Question>>

    suspend fun fetchQuestionsByCategory(category: Category): Result<List<Question>>

    suspend fun searchQuestions(query: String): Result<List<Question>>
}
