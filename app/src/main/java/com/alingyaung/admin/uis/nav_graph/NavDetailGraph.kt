package com.alingyaung.admin.uis.nav_graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alingyaung.admin.uis.screen.BookDetailScreen

fun NavGraphBuilder.navDetailGraph(navController: NavController,title:(String) -> Unit) {
    navigation(
        startDestination = DetailScreen.BookDetail.route,
        route = Graph.DETAILS
    ) {
        composable(route = DetailScreen.BookDetail.route) {
            BookDetailScreen()
        }
        composable(route = DetailScreen.Overview.route) {
        }
    }
}

sealed class DetailScreen(val route: String) {
    object BookDetail : DetailScreen("book_detail")
    object Overview : DetailScreen("over_view")
}