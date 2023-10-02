package com.alingyaung.admin.uis.nav_graph

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alingyaung.admin.uis.screen.BookDetailScreen

fun NavGraphBuilder.navDetailGraph(navController: NavController,title:(String) -> Unit,bookId: String = "") {
    navigation(
        startDestination = DetailScreen.BookDetail.getBookId(bookId),
        route = Graph.DETAILS
    ) {
        composable(route = DetailScreen.BookDetail.route) {navBackStackEntry ->
            val arguments = navBackStackEntry.arguments
            val id = arguments?.getString("id") ?: ""
            Log.d("%s",id)
            Log.d("%s %s",bookId)
            BookDetailScreen(navController,bookId)
        }
        composable(route = DetailScreen.Overview.route) {
        }
    }
}

sealed class DetailScreen(val route: String) {
    object BookDetail : DetailScreen("book_detail/{id}"){
        fun getBookId(id:String = "0") = "book_detail/$id"
    }
    object Overview : DetailScreen("over_view")
}