package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.request.ExamQuestionRequest
import com.peto.droidmorning.data.model.request.LikeRequest
import com.peto.droidmorning.data.model.response.CategoryCountResponse
import com.peto.droidmorning.data.model.response.ExamQuestionResponse
import com.peto.droidmorning.data.model.response.QuestionResponse
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc

class DefaultRemoteQuestionDataSource(
    private val postgrest: Postgrest,
    private val auth: Auth,
) : RemoteQuestionDataSource {
    override suspend fun fetchExamQuestions(): List<QuestionResponse> {
        val params = mapOf(RPC_FETCH_QUESTIONS_PARAM_NAME to uid())
        return postgrest
            .rpc(RPC_FETCH_QUESTIONS, params)
            .decodeList<QuestionResponse>()
    }

    override suspend fun fetchExamQuestions(
        category: List<String>,
        count: Int,
    ): List<ExamQuestionResponse> {
        val params = ExamQuestionRequest(category, count)
        return postgrest
            .rpc(RPC_GENERATE_EXAM_QUESTIONS, params)
            .decodeList<ExamQuestionResponse>()
    }

    override suspend fun addLike(questionId: Long) {
        val request = LikeRequest(uid(), questionId)
        postgrest
            .from(FAVORITES_TABLE)
            .insert(request)
    }

    override suspend fun removeLike(questionId: Long) {
        postgrest
            .from(FAVORITES_TABLE)
            .delete {
                filter {
                    eq(USER_ID_COLUMN, uid())
                    eq(QUESTION_ID_COLUMN, questionId)
                }
            }
    }

    override suspend fun fetchCategoryCount(): List<CategoryCountResponse> =
        postgrest
            .rpc(RPC_CATEGORY_COUNT)
            .decodeList<CategoryCountResponse>()

    private fun uid(): String =
        auth.currentSessionOrNull()?.user?.id
            ?: throw IllegalStateException("User not logged in")

    companion object {
        private const val RPC_FETCH_QUESTIONS = "fetch_questions"
        private const val RPC_FETCH_QUESTIONS_PARAM_NAME = "uid"

        private const val RPC_CATEGORY_COUNT = "fetch_question_counts_by_category"
        private const val RPC_GENERATE_EXAM_QUESTIONS = "generate_exam_questions"

        private const val FAVORITES_TABLE = "favorites"
        private const val USER_ID_COLUMN = "user_id"
        private const val QUESTION_ID_COLUMN = "question_id"
    }
}
