package com.peto.droidmorning.exam.complete.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.peto.droidmorning.exam.complete.ExamCompleteScreen
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.navigation.NavRoutes

class ExamCompleteNavGraphContributor : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.ExamCompleteGraph

    override val startDestination: String
        get() = NavRoutes.ExamComplete.ROUTE

    override val priority: Int = 4

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(
                route = NavRoutes.ExamComplete.ROUTE,
                arguments =
                    listOf(
                        navArgument("examId") {
                            type = NavType.LongType
                        },
                    ),
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.ExamComplete>()

                ExamCompleteScreen(
                    examId = args.examId,
                    onNavigateToQuestions = {
                        navController.popBackStack(NavRoutes.Main.route, inclusive = false)
                    },
                )
            }
        }
    }
}
