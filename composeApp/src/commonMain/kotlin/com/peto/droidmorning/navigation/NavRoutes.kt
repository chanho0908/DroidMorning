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

    data object ExamProgressGraph : NavRoutes("exam_progress_graph")

    data object ExamProgress : NavRoutes("exam_progress")

    data object ExamCompleteGraph : NavRoutes("exam_complete_graph")

    @Serializable
    data class ExamComplete(
        val examId: Long,
    ) : NavRoutes(route = ROUTE) {
        companion object {
            const val ROUTE: String = "exam_complete/{examId}"

            fun createRoute(examId: Long): String = "exam_complete/$examId"
        }
    }

    @Serializable
    data class ExamDetail(
        val examId: Long,
    ) : NavRoutes(route = ROUTE) {
        companion object Companion {
            const val ROUTE: String = "exam_detail/{examId}"

            fun createRoute(examId: Long): String = "exam_detail/$examId"
        }
    }
}
