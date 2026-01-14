package com.peto.droidmorning.data.model

private typealias ColumValues = Any?

data class QuestionRequest(
    val userId: String,
    val categories: List<String>? = null,
    val query: String? = null,
    val solve: Boolean? = null,
    val favorite: Boolean? = null,
) {
    fun toSupabaseParams(): Map<String, ColumValues> =
        mapOf(
            USER_ID_COLUMN to userId,
            SEARCH_QUERY_COLUMN to query,
            CATEGORIES_COLUMN to categories,
            SOLVED_ONLY_COLUMN to solve,
            FAVORITE_ONLY_COLUMN to favorite,
        )

    companion object {
        private const val USER_ID_COLUMN = "p_user_id"
        private const val SEARCH_QUERY_COLUMN = "p_query"
        private const val CATEGORIES_COLUMN = "p_categories"
        private const val SOLVED_ONLY_COLUMN = "p_solved_only"
        private const val FAVORITE_ONLY_COLUMN = "p_favorite_only"
    }
}
