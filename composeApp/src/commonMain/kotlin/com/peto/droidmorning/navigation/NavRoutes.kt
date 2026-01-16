package com.peto.droidmorning.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class NavRoutes(
    @Transient val route: String = "",
) {
    data object LoginGraph : NavRoutes("login_graph")

    data object Login : NavRoutes("login")

    data object MainGraph : NavRoutes("main_graph")

    data object Main : NavRoutes("main")

    data object Test : NavRoutes("test")

    data object History : NavRoutes("history")

    data object Profile : NavRoutes("profile")

    data object QuestionDetailGraph : NavRoutes("question_detail_graph")

    @Serializable
    data class QuestionDetail(
        val questionId: Long,
    ) : NavRoutes(route = ROUTE) {
        companion object {
            const val ROUTE: String = "question_detail/{questionId}"

            fun createRoute(questionId: Long): String = "question_detail/$questionId"
        }
    }
}
