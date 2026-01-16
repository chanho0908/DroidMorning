package com.peto.droidmorning.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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
            composable(NavRoutes.Main.route) {
                MainScreen(
                    onNavigateToQuestionDetail = { questionId ->
                        navController.navigate(NavRoutes.QuestionDetail.createRoute(questionId))
                    },
                )
            }
        }
    }
}
