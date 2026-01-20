package com.peto.droidmorning.data.datasource.question.remote

import com.peto.droidmorning.core.network.AuthClient
import com.peto.droidmorning.core.network.PostgrestClient
import com.peto.droidmorning.core.network.PostgrestFilter
import com.peto.droidmorning.data.model.request.ExamQuestionRequest
import com.peto.droidmorning.data.model.request.LikeRequest
import com.peto.droidmorning.data.model.response.CategoryCountResponse
import com.peto.droidmorning.data.model.response.ExamQuestionResponse
import com.peto.droidmorning.data.model.response.QuestionResponse
import com.peto.droidmorning.data.util.JsonUtil
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class DefaultRemoteQuestionDataSource(
    private val postgrest: PostgrestClient,
    private val authClient: AuthClient,
) : RemoteQuestionDataSource {
    private val json = JsonUtil.defaultJson

    override suspend fun fetchExamQuestions(): List<QuestionResponse> =
        postgrest
            .rpc(
                function = RPC_FETCH_QUESTIONS,
                parameters =
                    json
                        .encodeToJsonElement(mapOf(RPC_FETCH_QUESTIONS_PARAM_NAME to uid()))
                        .jsonObject,
            ).let { data ->
                JsonUtil.decode(
                    data,
                    ListSerializer(QuestionResponse.serializer()),
                )
            }

    override suspend fun fetchExamQuestions(
        category: List<String>,
        count: Int,
    ): List<ExamQuestionResponse> =
        postgrest
            .rpc(
                function = RPC_GENERATE_EXAM_QUESTIONS,
                parameters =
                    json
                        .encodeToJsonElement(ExamQuestionRequest(category, count))
                        .jsonObject,
            ).let { data ->
                JsonUtil.decode(
                    data,
                    ListSerializer(ExamQuestionResponse.serializer()),
                )
            }

    override suspend fun addLike(questionId: Long) {
        postgrest.insert(
            table = FAVORITES_TABLE,
            body =
                json
                    .encodeToJsonElement(LikeRequest(uid(), questionId))
                    .jsonObject,
        )
    }

    override suspend fun removeLike(questionId: Long) {
        postgrest.delete(
            table = FAVORITES_TABLE,
            filters =
                listOf(
                    PostgrestFilter(USER_ID_COLUMN, uid()),
                    PostgrestFilter(QUESTION_ID_COLUMN, questionId),
                ),
        )
    }

    override suspend fun fetchCategoryCount(): List<CategoryCountResponse> =
        postgrest
            .rpc(RPC_CATEGORY_COUNT)
            .let { data ->
                JsonUtil.decode(
                    data,
                    ListSerializer(CategoryCountResponse.serializer()),
                )
            }

    private fun uid(): String =
        authClient.currentUserId()
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
