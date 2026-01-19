package com.peto.droidmorning.data.repository

import com.peto.droidmorning.data.datasource.exam.remote.RemoteExamDataSource
import com.peto.droidmorning.data.model.response.toDomain
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.domain.model.exam.ExamDetail
import com.peto.droidmorning.domain.model.exam.ExamHistory
import com.peto.droidmorning.domain.model.exam.Exams
import com.peto.droidmorning.domain.repository.ExamRepository

class DefaultExamRepository(
    private val remoteExamDataSource: RemoteExamDataSource,
) : ExamRepository {
    override suspend fun submitExam(
        exam: Exams,
        categories: List<Category>,
    ): Result<Long> = runCatching { remoteExamDataSource.submitExam(exam, categories) }

    override suspend fun fetchExamHistory(): Result<List<ExamHistory>> =
        runCatching {
            remoteExamDataSource
                .fetchExamHistory()
                .map { it.toDomain() }
        }

    override suspend fun fetchExamDetail(examId: Long): Result<List<ExamDetail>> =
        runCatching {
            remoteExamDataSource
                .fetchExamDetail(examId)
                .map { it.toDomain() }
        }

    override suspend fun deleteExam(examId: Long): Result<Unit> =
        runCatching {
            remoteExamDataSource.deleteExam(examId)
        }
}
