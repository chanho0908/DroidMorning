package com.peto.droidmorning.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.peto.droidmorning.exam.detail.ExamDetailScreen
import com.peto.droidmorning.main.MainScreen
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.navigation.NavRoutes

class MainNavGraphContributor : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.MainGraph
    override val startDestination: String
        get() = NavRoutes.Main.route
    override val priority: Int = 1

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.Main.route) { backStackEntry ->
                MainScreen(
                    onNavigateToQuestionDetail = { questionId ->
                        navController.navigate(NavRoutes.QuestionDetail.createRoute(questionId))
                    },
                    onNavigateToExamProgress = { questionCount, categories ->
                        navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set("questionCount", questionCount)
                            set("categories", categories.map { it.name }.toTypedArray())
                        }
                        navController.navigate(NavRoutes.ExamProgressGraph.route)
                    },
                    onNavigateToExamResult = { examId ->
                        navController.navigate(NavRoutes.ExamDetail.createRoute(examId))
                    },
                    savedStateHandle = backStackEntry.savedStateHandle,
                )
            }

            composable(
                route = NavRoutes.ExamDetail.ROUTE,
                arguments =
                    listOf(
                        navArgument("examId") {
                            type = NavType.LongType
                        },
                    ),
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.ExamDetail>()
                ExamDetailScreen(
                    examId = args.examId,
                    onNavigateBack = {
                        navController.popBackStack(NavRoutes.Main.route, inclusive = false)
                    },
                )
            }
        }
    }
}
