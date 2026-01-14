package com.peto.droidmorning.question.vm

import androidx.compose.runtime.Stable
import com.peto.droidmorning.domain.model.Category
import com.peto.droidmorning.domain.model.Filter
import com.peto.droidmorning.domain.model.Question
import com.peto.droidmorning.domain.model.Questions
import com.peto.droidmorning.domain.model.SearchQuery
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

@Stable
data class QuestionUiState(
    private val allQuestions: Questions = Questions(emptyList()),
    private val _filteredQuestions: ImmutableList<Question>? = null,
    val filter: Filter = Filter(),
    val showCategoryFilters: Boolean = false,
    val isLoading: Boolean = false,
    val isFiltering: Boolean = false,
) {
    val searchQuery: SearchQuery get() = filter.searchQuery
    val selectedCategories: ImmutableSet<Category> get() = filter.categories.toSet().toImmutableSet()
    val showSolvedOnly: Boolean get() = filter.showSolvedOnly
    val showFavoritesOnly: Boolean get() = filter.showFavoritesOnly

    val filteredQuestions: ImmutableList<Question>
        get() = _filteredQuestions ?: computeFilteredQuestions()

    fun updateQuestions(questions: Questions): QuestionUiState {
        val newState = copy(allQuestions = questions, isLoading = false)
        return newState.copy(_filteredQuestions = newState.computeFilteredQuestions())
    }

    fun updateSearchQuery(query: String): QuestionUiState {
        val newState = copy(filter = filter.updateSearchQuery(query))
        return newState.copy(_filteredQuestions = newState.computeFilteredQuestions())
    }

    fun toggleCategory(category: Category): QuestionUiState {
        val newState = copy(filter = filter.toggleCategory(category))
        return newState.copy(_filteredQuestions = newState.computeFilteredQuestions())
    }

    fun toggleCategoryFilters(): QuestionUiState = copy(showCategoryFilters = !showCategoryFilters)

    fun toggleSolvedFilter(): QuestionUiState {
        val newState = copy(filter = filter.toggleSolvedFilter())
        return newState.copy(_filteredQuestions = newState.computeFilteredQuestions())
    }

    fun toggleFavoritesFilter(): QuestionUiState {
        val newState = copy(filter = filter.toggleFavoritesFilter())
        return newState.copy(_filteredQuestions = newState.computeFilteredQuestions())
    }

    fun loading(isLoading: Boolean): QuestionUiState = copy(isLoading = isLoading)

    fun filtering(): QuestionUiState = copy(isFiltering = true)

    fun filterCompleted(): QuestionUiState = copy(isFiltering = false)

    private fun computeFilteredQuestions(): ImmutableList<Question> {
        if (allQuestions.isEmpty) return persistentListOf()
        if (filter.isEmpty()) return allQuestions.toList().toImmutableList()
        return allQuestions
            .applyFilters(filter)
            .toList()
            .toImmutableList()
    }
}
