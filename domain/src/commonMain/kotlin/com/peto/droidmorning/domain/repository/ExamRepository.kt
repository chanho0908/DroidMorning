package com.peto.droidmorning.domain.repository

import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import com.peto.droidmorning.domain.model.exam.ExamHistory
import com.peto.droidmorning.domain.model.exam.Exams

interface ExamRepository {
    suspend fun submitExam(
        exam: Exams,
        categories: List<Category>,
    ): Result<Long>

    suspend fun fetchExamHistory(): Result<List<ExamHistory>>

    suspend fun fetchExamDetail(examId: Long): Result<List<ExamDetail>>

    suspend fun deleteExam(examId: Long): Result<Unit>
}
