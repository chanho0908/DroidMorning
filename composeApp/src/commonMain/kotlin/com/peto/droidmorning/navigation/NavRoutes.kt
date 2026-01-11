package com.peto.droidmorning.navigation

sealed class NavRoutes(
    val route: String,
) {
    object LoginGraph : NavRoutes("login_graph")

    object Login : NavRoutes("login")
}
