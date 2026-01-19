package com.peto.droidmorning.domain.model.question

import com.peto.droidmorning.domain.model.Filter
import com.peto.droidmorning.domain.model.SearchQuery
import com.peto.droidmorning.domain.model.category.Categories

data class Questions(
    private val values: List<Question>,
) {
    val size: Int
        get() = values.size

    val isEmpty: Boolean
        get() = values.isEmpty()

    fun applyFilters(filter: Filter): Questions =
        filterBySearchQuery(filter.searchQuery)
            .filterByCategory(filter.categories)
            .filterBySolved(filter.solved)
            .filterByLiked(filter.liked)

    private fun filterBySearchQuery(query: SearchQuery): Questions {
        if (query.isEmpty()) return this
        return copy(
            values =
                values.filter { question ->
                    question.isTitleMatched(query)
                },
        )
    }

    private fun filterByCategory(categories: Categories): Questions {
        if (categories.isEmpty()) return this
        return copy(
            values =
                values.filter { question ->
                    categories.contains(question.category)
                },
        )
    }

    private fun filterBySolved(showSolvedOnly: Boolean): Questions {
        if (!showSolvedOnly) return this
        return copy(values = values.filter { it.isSolved })
    }

    private fun filterByLiked(showLikedOnly: Boolean): Questions {
        if (!showLikedOnly) return this
        return copy(values = values.filter { it.isLiked })
    }

    fun toList(): List<Question> = values.toList()
}
