package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.data.model.QuestionResponse
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.TextSearchType

class DefaultRemoteQuestionDataSource(
    private val postgrest: Postgrest,
) : RemoteQuestionDataSource {
    override suspend fun fetchQuestions(): List<QuestionResponse> =
        postgrest
            .from(TABLE_NAME)
            .select(columns = Columns.ALL) {
                order(column = ORDER_BY_CREATED_AT, order = Order.DESCENDING)
            }.decodeList<QuestionResponse>()

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
        private const val TABLE_NAME = "questions"
        private const val CATEGORY_COLUMN = "category"
        private const val FTS_COLUMN = "fts"
        private const val ORDER_BY_CREATED_AT = "created_at"
    }
}
