package com.peto.droidmorning.data.datasource.exam.remote

import com.peto.droidmorning.data.model.request.toRequest
import com.peto.droidmorning.data.model.response.ExamDetailResponse
import com.peto.droidmorning.data.model.response.ExamHistoryResponse
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.Exams
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc

class DefaultRemoteExamDataSource(
    private val postgrest: Postgrest,
    private val auth: Auth,
) : RemoteExamDataSource {
    override suspend fun submitExam(
        exam: Exams,
        categories: List<Category>,
    ): Long =
        postgrest
            .rpc(RPC_CREATE_EXAM, exam.toRequest(uid(), categories))
            .decodeAs<Long>()

    override suspend fun fetchExamHistory(): List<ExamHistoryResponse> =
        postgrest
            .from(TABLE_EXAMS)
            .select(Columns.ALL) {
                filter {
                    eq(USER_ID_COLUMN, uid())
                }
                order(UPDATED_AT_COLUMN, Order.DESCENDING)
            }.decodeList<ExamHistoryResponse>()

    override suspend fun fetchExamDetail(examId: Long): List<ExamDetailResponse> {
        val params = mapOf(RPC_PARAM_EXAM_ID to examId)
        return postgrest
            .rpc(RPC_GET_EXAM_DETAIL, params)
            .decodeList<ExamDetailResponse>()
    }

    override suspend fun deleteExam(examId: Long) {
        postgrest
            .from(TABLE_EXAMS)
            .delete {
                filter {
                    eq(ID_COLUMN, examId)
                    eq(USER_ID_COLUMN, uid())
                }
            }
    }

    private fun uid(): String =
        auth.currentSessionOrNull()?.user?.id
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
