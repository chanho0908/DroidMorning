package com.peto.droidmorning.domain.model

data class Filter(
    val searchQuery: SearchQuery = SearchQuery(""),
    val categories: Categories = Categories(emptySet()),
    val solved: Boolean = false,
    val liked: Boolean = false,
) {
    fun isEmpty(): Boolean =
        searchQuery.isEmpty() &&
                categories.isEmpty() &&
                !solved && !liked

    fun applySearchQuery(query: String): Filter = copy(searchQuery = SearchQuery(query))

    fun updateCategory(category: Category): Filter =
        copy(
            categories =
                if (categories.contains(category)) {
                    categories.remove(category)
                } else {
                    categories.add(category)
                },
        )

    fun applySolvedFilter(): Filter = copy(solved = true)
    fun clearSolvedFilter(): Filter = copy(solved = false)

    fun applyFavoritesFilter(): Filter = copy(liked = true)
    fun clearFavoritesFilter(): Filter = copy(liked = false)
}
