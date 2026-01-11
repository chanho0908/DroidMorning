package com.peto.droidmorning.navigation

sealed class NavRoutes(
    val route: String,
) {
    data object LoginGraph : NavRoutes("login_graph")

    data object Login : NavRoutes("login")

    data object MainGraph : NavRoutes("main_graph")

    data object Main : NavRoutes("main")

    data object Test : NavRoutes("test")

    data object History : NavRoutes("history")

    data object Profile : NavRoutes("profile")
}
