package com.peto.droidmorning.questions.list.model

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Filter
import com.peto.droidmorning.domain.model.Question
import com.peto.droidmorning.domain.model.Questions
import com.peto.droidmorning.domain.model.SearchQuery
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

@Stable
data class QuestionUiState(
    private val allQuestions: Questions = Questions(emptyList()),
    val filter: Filter = Filter(),
    val showCategoryFilters: Boolean = false,
    val isLoading: Boolean = false,
    val isFiltering: Boolean = false,
) {
    val searchQuery: SearchQuery get() = filter.searchQuery
    val selectedCategories: ImmutableSet<Category>
        get() = filter.categories.toSet().toImmutableSet()
    val showSolvedOnly: Boolean get() = filter.solved
    val showLikedOnly: Boolean get() = filter.liked

    val filteredQuestions: ImmutableList<Question>
        get() {
            if (filter.isEmpty()) return allQuestions.toList().toImmutableList()
            return allQuestions
                .applyFilters(filter)
                .toList()
                .toImmutableList()
        }

    fun updateQuestions(questions: Questions): QuestionUiState = copy(allQuestions = questions, isLoading = false)

    fun updateSearchQuery(query: String): QuestionUiState = copy(filter = filter.applySearchQuery(query))

    fun addCategory(category: Category): QuestionUiState = copy(filter = filter.addCategory(category))

    fun removeCategory(category: Category): QuestionUiState = copy(filter = filter.removeCategory(category))

    fun toggleCategoryFilters(): QuestionUiState = copy(showCategoryFilters = !showCategoryFilters)

    fun applySolvedFilter(): QuestionUiState = copy(filter = filter.applySolvedFilter())

    fun clearSolvedFilter(): QuestionUiState = copy(filter = filter.clearSolvedFilter())

    fun applyLikedFilter(): QuestionUiState = copy(filter = filter.applyLikedFilter())

    fun clearLikedFilter(): QuestionUiState = copy(filter = filter.clearLikedFilter())

    fun toggleQuestionLike(questionId: Long): QuestionUiState {
        val updatedList =
            allQuestions.toList().map { question ->
                if (question.id == questionId) {
                    question.copy(isLiked = !question.isLiked)
                } else {
                    question
                }
            }
        return copy(allQuestions = Questions(updatedList))
    }

    fun loading(isLoading: Boolean): QuestionUiState = copy(isLoading = isLoading)

    fun filtering(): QuestionUiState = copy(isFiltering = true)

    fun filterClear(): QuestionUiState = copy(isFiltering = false)
}
