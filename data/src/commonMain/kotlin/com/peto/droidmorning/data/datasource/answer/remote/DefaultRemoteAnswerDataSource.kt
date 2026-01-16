package com.peto.droidmorning.data.datasource.answer.remote

import com.peto.droidmorning.data.model.request.CreateAnswerRequest
import com.peto.droidmorning.data.model.request.RpcDefaultRequest
import com.peto.droidmorning.data.model.request.UpdateAnswerRequest
import com.peto.droidmorning.data.model.response.AnswerHistoryResponse
import com.peto.droidmorning.data.model.response.CurrentAnswerResponse
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc

class DefaultRemoteAnswerDataSource(
    private val postgrest: Postgrest,
    private val auth: Auth,
) : RemoteAnswerDataSource {
    override suspend fun fetchCurrentAnswer(questionId: Long): CurrentAnswerResponse? =
        postgrest
            .from(ANSWERS_CURRENT_TABLE)
            .select(Columns.ALL) {
                filter {
                    eq(USER_ID_COLUMN, uid())
                    eq(QUESTION_ID_COLUMN, questionId)
                }
            }.decodeSingleOrNull<CurrentAnswerResponse>()

    override suspend fun fetchAnswerHistory(questionId: Long): List<AnswerHistoryResponse> =
        postgrest
            .from(ANSWER_HISTORY_TABLE)
            .select(Columns.ALL) {
                filter {
                    eq(USER_ID_COLUMN, uid())
                    eq(QUESTION_ID_COLUMN, questionId)
                }
                order(CREATED_AT_COLUMN, order = Order.DESCENDING)
            }.decodeList<AnswerHistoryResponse>()

    override suspend fun createAnswer(
        questionId: Long,
        content: String,
    ) {
        postgrest.rpc(
            function = RPC_UPSERT_ANSWER_CURRENT,
            parameters = CreateAnswerRequest(uid(), questionId, content),
        )
    }

    override suspend fun modifyAnswer(
        questionId: Long,
        content: String,
    ) {
        postgrest
            .from(ANSWERS_CURRENT_TABLE)
            .update(UpdateAnswerRequest(content)) {
                filter {
                    eq(USER_ID_COLUMN, uid())
                    eq(QUESTION_ID_COLUMN, questionId)
                }
            }
    }

    override suspend fun deleteCurrentAnswer(questionId: Long) {
        postgrest.rpc(
            function = RPC_DELETE_ANSWER_CURRENT,
            parameters = RpcDefaultRequest(uid(), questionId),
        )
    }

    override suspend fun deleteAnswerHistory(historyId: Long) {
        postgrest
            .from(ANSWER_HISTORY_TABLE)
            .delete {
                filter {
                    eq(ID_COLUMN, historyId)
                    eq(USER_ID_COLUMN, uid())
                }
            }
    }

    private fun uid(): String =
        auth.currentSessionOrNull()?.user?.id
            ?: throw IllegalStateException("User not logged in")

    companion object {
        private const val RPC_UPSERT_ANSWER_CURRENT = "upsert_answer_current"
        private const val RPC_DELETE_ANSWER_CURRENT = "delete_and_restore_answer"

        private const val ANSWERS_CURRENT_TABLE = "answers_current"
        private const val ANSWER_HISTORY_TABLE = "answer_history"

        private const val USER_ID_COLUMN = "user_id"
        private const val QUESTION_ID_COLUMN = "question_id"
        private const val CREATED_AT_COLUMN = "created_at"
        private const val ID_COLUMN = "id"
    }
}
