package com.peto.droidmorning.exam.main.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.category.Category

@Stable
data class ExamCreateState(
    val selectedQuestionCount: Int = 5,
    val selectedCategories: List<Category> = emptyList(),
    val categoryCountMap: Map<Category, Long> = emptyMap(),
) {
    val availableQuestionCount: Long
        get() =
            selectedCategories.sumOf { category ->
                categoryCountMap[category] ?: 0L
            }

    val isValid: Boolean
        get() =
            selectedCategories.isNotEmpty() &&
                availableQuestionCount > 0 &&
                selectedQuestionCount <= availableQuestionCount

    fun selectQuestionCount(count: Int): ExamCreateState = copy(selectedQuestionCount = count)

    fun toggleCategory(category: Category): ExamCreateState {
        val newCategories =
            if (selectedCategories.contains(category)) {
                selectedCategories - category
            } else {
                selectedCategories + category
            }
        return copy(selectedCategories = newCategories)
    }

    fun updateCategoryCounts(countMap: Map<Category, Long>): ExamCreateState = copy(categoryCountMap = countMap)
}
