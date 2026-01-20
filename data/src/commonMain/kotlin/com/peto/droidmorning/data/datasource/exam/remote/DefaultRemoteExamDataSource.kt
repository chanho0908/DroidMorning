package com.peto.droidmorning.data.datasource.exam.remote

import com.peto.droidmorning.core.network.AuthClient
import com.peto.droidmorning.core.network.PostgrestClient
import com.peto.droidmorning.core.network.PostgrestFilter
import com.peto.droidmorning.core.network.PostgrestOrder
import com.peto.droidmorning.data.model.request.toRequest
import com.peto.droidmorning.data.model.response.ExamDetailResponse
import com.peto.droidmorning.data.model.response.ExamHistoryResponse
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.Exams
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class DefaultRemoteExamDataSource(
    private val postgrest: PostgrestClient,
    private val authClient: AuthClient,
) : RemoteExamDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun submitExam(
        exam: Exams,
        categories: List<Category>,
    ): Long =
        postgrest
            .rpc(
                function = RPC_CREATE_EXAM,
                parameters =
                    json
                        .encodeToJsonElement(exam.toRequest(uid(), categories))
                        .jsonObject,
            ).let { data ->
                json.decodeFromString(Long.serializer(), data)
            }

    override suspend fun fetchExamHistory(): List<ExamHistoryResponse> =
        postgrest
            .select(
                table = TABLE_EXAMS,
                filters = listOf(PostgrestFilter(USER_ID_COLUMN, uid())),
                order = PostgrestOrder(UPDATED_AT_COLUMN, descending = true),
            ).let { data ->
                json.decodeFromString(
                    ListSerializer(ExamHistoryResponse.serializer()),
                    data,
                )
            }

    override suspend fun fetchExamDetail(examId: Long): List<ExamDetailResponse> =
        postgrest
            .rpc(
                function = RPC_GET_EXAM_DETAIL,
                parameters =
                    json
                        .encodeToJsonElement(mapOf(RPC_PARAM_EXAM_ID to examId))
                        .jsonObject,
            ).let { data ->
                json.decodeFromString(
                    ListSerializer(ExamDetailResponse.serializer()),
                    data,
                )
            }

    override suspend fun deleteExam(examId: Long) {
        postgrest.delete(
            table = TABLE_EXAMS,
            filters =
                listOf(
                    PostgrestFilter(ID_COLUMN, examId),
                    PostgrestFilter(USER_ID_COLUMN, uid()),
                ),
        )
    }

    private fun uid(): String =
        authClient.currentUserId()
            ?: throw IllegalStateException("User not logged in")

    companion object {
        private const val RPC_CREATE_EXAM = "create_exam_with_items"
        private const val RPC_GET_EXAM_DETAIL = "get_exam_detail"
        private const val RPC_PARAM_EXAM_ID = "p_exam_id"

        private const val TABLE_EXAMS = "exams"

        private const val ID_COLUMN = "id"
        private const val USER_ID_COLUMN = "user_id"
        private const val UPDATED_AT_COLUMN = "updated_at"
    }
}
