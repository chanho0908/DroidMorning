package com.peto.droidmorning.data.datasource.exam.remote

import com.peto.droidmorning.data.model.response.ExamDetailResponse
import com.peto.droidmorning.data.model.response.ExamHistoryResponse
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.Exams

interface RemoteExamDataSource {
    suspend fun submitExam(
        exam: Exams,
        categories: List<Category>,
    ): Long

    suspend fun fetchExamHistory(): List<ExamHistoryResponse>

    suspend fun fetchExamDetail(examId: Long): List<ExamDetailResponse>

    suspend fun deleteExam(examId: Long)
}
