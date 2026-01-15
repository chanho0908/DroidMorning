package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.QuestionResponse
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import io.github.jan.supabase.postgrest.rpc

class DefaultRemoteQuestionDataSource(
    private val postgrest: Postgrest,
    private val auth: Auth
) : RemoteQuestionDataSource {
    override suspend fun fetchQuestions(): List<QuestionResponse> {
        val uid = auth.currentSessionOrNull()?.user?.id ?: return emptyList()
        val params = mapOf(RPC_FETCH_QUESTIONS_PARAM_NAME to uid)
        return postgrest
            .rpc(RPC_FETCH_QUESTIONS, params)
            .decodeList<QuestionResponse>()
    }

    override suspend fun fetchQuestionsByCategory(category: String): List<QuestionResponse> =
        postgrest
            .from(TABLE_NAME)
            .select(columns = Columns.ALL) {
                filter {
                    eq(CATEGORY_COLUMN, category)
                }
                order(column = ORDER_BY_CREATED_AT, order = Order.DESCENDING)
            }.decodeList<QuestionResponse>()

    override suspend fun searchQuestions(query: String): List<QuestionResponse> =
        postgrest
            .from(TABLE_NAME)
            .select(columns = Columns.ALL) {
                filter {
                    textSearch(FTS_COLUMN, query, TextSearchType.WEBSEARCH)
                }
                order(column = ORDER_BY_CREATED_AT, order = Order.DESCENDING)
            }.decodeList<QuestionResponse>()

    companion object {
        private const val RPC_FETCH_QUESTIONS = "fetch_questions"
        private const val RPC_FETCH_QUESTIONS_PARAM_NAME = "uid"
        private const val TABLE_NAME = "questions"
        private const val CATEGORY_COLUMN = "category"
        private const val FTS_COLUMN = "fts"
        private const val ORDER_BY_CREATED_AT = "created_at"
    }
}
