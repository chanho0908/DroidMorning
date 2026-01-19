package com.peto.droidmorning.domain.model.exam

data class Exams(
    val values: List<Exam> = emptyList(),
) {
    operator fun get(questionId: Long): String = values.firstOrNull { it.questionId == questionId }?.answer.orEmpty()

    fun updateAnswer(
        questionId: Long,
        answer: String,
    ): Exams {
        val existingExamIndex = values.indexOfFirst { it.questionId == questionId }

        return if (existingExamIndex >= 0) {
            val updatedValues =
                values.toMutableList().apply {
                    set(existingExamIndex, Exam(questionId, answer))
                }
            copy(values = updatedValues)
        } else {
            copy(values = values + Exam(questionId, answer))
        }
    }
}
