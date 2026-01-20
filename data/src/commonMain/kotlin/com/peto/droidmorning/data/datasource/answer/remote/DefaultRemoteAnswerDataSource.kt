package com.peto.droidmorning.data.datasource.answer.remote

import com.peto.droidmorning.core.network.AuthClient
import com.peto.droidmorning.core.network.PostgrestClient
import com.peto.droidmorning.core.network.PostgrestFilter
import com.peto.droidmorning.core.network.PostgrestOrder
import com.peto.droidmorning.data.model.request.CreateAnswerRequest
import com.peto.droidmorning.data.model.request.RpcDefaultRequest
import com.peto.droidmorning.data.model.request.UpdateAnswerRequest
import com.peto.droidmorning.data.model.response.AnswerHistoryResponse
import com.peto.droidmorning.data.model.response.CurrentAnswerResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class DefaultRemoteAnswerDataSource(
    private val postgrest: PostgrestClient,
    private val authClient: AuthClient,
) : RemoteAnswerDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchCurrentAnswer(questionId: Long): CurrentAnswerResponse? =
        postgrest
            .select(
                table = ANSWERS_CURRENT_TABLE,
                filters =
                    listOf(
                        PostgrestFilter(USER_ID_COLUMN, uid()),
                        PostgrestFilter(QUESTION_ID_COLUMN, questionId),
                    ),
            ).let { data ->
                json
                    .decodeFromString(
                        ListSerializer(CurrentAnswerResponse.serializer()),
                        data,
                    ).firstOrNull()
            }

    override suspend fun fetchAnswerHistory(questionId: Long): List<AnswerHistoryResponse> =
        postgrest
            .select(
                table = ANSWER_HISTORY_TABLE,
                filters =
                    listOf(
                        PostgrestFilter(USER_ID_COLUMN, uid()),
                        PostgrestFilter(QUESTION_ID_COLUMN, questionId),
                    ),
                order = PostgrestOrder(CREATED_AT_COLUMN, descending = true),
            ).let { data ->
                json.decodeFromString(
                    ListSerializer(AnswerHistoryResponse.serializer()),
                    data,
                )
            }

    override suspend fun createAnswer(
        questionId: Long,
        content: String,
    ) {
        postgrest.rpc(
            function = RPC_UPSERT_ANSWER_CURRENT,
            parameters =
                json
                    .encodeToJsonElement(CreateAnswerRequest(uid(), questionId, content))
                    .jsonObject,
        )
    }

    override suspend fun modifyAnswer(
        questionId: Long,
        content: String,
    ) {
        postgrest.update(
            table = ANSWERS_CURRENT_TABLE,
            body = json.encodeToJsonElement(UpdateAnswerRequest(content)),
            filters =
                listOf(
                    PostgrestFilter(USER_ID_COLUMN, uid()),
                    PostgrestFilter(QUESTION_ID_COLUMN, questionId),
                ),
        )
    }

    override suspend fun deleteCurrentAnswer(questionId: Long) {
        postgrest.rpc(
            function = RPC_DELETE_ANSWER_CURRENT,
            parameters =
                json
                    .encodeToJsonElement(RpcDefaultRequest(uid(), questionId))
                    .jsonObject,
        )
    }

    override suspend fun deleteAnswerHistory(historyId: Long) {
        postgrest.delete(
            table = ANSWER_HISTORY_TABLE,
            filters =
                listOf(
                    PostgrestFilter(ID_COLUMN, historyId),
                    PostgrestFilter(USER_ID_COLUMN, uid()),
                ),
        )
    }

    private fun uid(): String =
        authClient.currentUserId()
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
