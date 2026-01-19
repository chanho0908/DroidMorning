package com.peto.droidmorning.exam.progress.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.peto.droidmorning.domain.model.category.Category
import com.peto.droidmorning.exam.progress.ExamProgressScreen
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.navigation.NavRoutes

class ExamProgressNavGraphContributor : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.ExamProgressGraph

    override val startDestination: String
        get() = NavRoutes.ExamProgress.route

    override val priority: Int = 3

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.ExamProgress.route) { backStackEntry ->
                val questionCount =
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Int>("questionCount") ?: 5

                val categoriesArray =
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Array<String>>("categories") ?: emptyArray()

                ExamProgressScreen(
                    questionCount = questionCount,
                    categories = categoriesArray.map { name -> Category.from(name) },
                    onNavigateToComplete = { examId ->
                        navController.navigate(NavRoutes.ExamComplete.createRoute(examId.toLong())) {
                            popUpTo(NavRoutes.Main.route)
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}
