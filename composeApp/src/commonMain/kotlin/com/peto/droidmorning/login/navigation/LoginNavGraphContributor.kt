package com.peto.droidmorning.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.peto.droidmorning.login.LoginScreen
import com.peto.droidmorning.navigation.NavGraphContributor
import com.peto.droidmorning.navigation.NavRoutes

class LoginNavGraphContributor : NavGraphContributor {
    override val graphRoute: NavRoutes = NavRoutes.LoginGraph
    override val startDestination: String = NavRoutes.Login.route
    override val priority: Int = 0

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.Login.route) {
                LoginScreen()
            }
        }
    }
}
