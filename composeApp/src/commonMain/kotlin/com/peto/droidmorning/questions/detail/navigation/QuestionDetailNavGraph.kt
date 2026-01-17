package com.peto.droidmorning.questions.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.navigation.NavRoutes
import com.peto.droidmorning.questions.detail.QuestionDetailScreen

class QuestionDetailNavGraph : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.QuestionDetailGraph
    override val startDestination: String
        get() = NavRoutes.QuestionDetail.ROUTE

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            startDestination = startDestination,
            route = graphRoute.route,
        ) {
            composable(
                route = NavRoutes.QuestionDetail.ROUTE,
                arguments = listOf(navArgument("questionId") { type = NavType.LongType }),
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.QuestionDetail>()
                val questionId = args.questionId

                QuestionDetailScreen(
                    questionId = questionId,
                    onNavigateBack = { result ->
                        navController.previousBackStackEntry?.savedStateHandle?.apply {
                            set(KEY_QUESTION_ID, questionId)
                            set(KEY_IS_LIKED, result.isLiked)
                            set(KEY_IS_SOLVED, result.isSolved)
                        }
                        navController.popBackStack()
                    },
                )
            }
        }
    }

    companion object {
        const val KEY_QUESTION_ID = "question_id"
        const val KEY_IS_LIKED = "is_liked"
        const val KEY_IS_SOLVED = "is_solved"
    }
}
