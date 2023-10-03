package com.alingyaung.admin.uis.nav_graph

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.uis.screen.BookDetailScreen
import com.alingyaung.admin.uis.viewmodel.BookDetailViewModel

fun NavGraphBuilder.navDetailGraph(navController: NavController,title:(String) -> Unit,bookId: String,
    isFav:Boolean,) {
    navigation(
        startDestination = DetailScreen.BookDetail.route,
        route = Graph.DETAILS
    ) {
        composable(route = DetailScreen.BookDetail.route) {navBackStackEntry ->
            val viewModel = hiltViewModel<BookDetailViewModel>()
            BookDetailScreen(
                navController,
                bookId,
                isFav,
                viewModel.bookDetailUIState.value,
                onEvent = viewModel::onEvent)
        }
        composable(route = DetailScreen.Overview.route) {
        }
    }
}

sealed class DetailScreen(val route: String) {
    object BookDetail : DetailScreen("book_detail")
    object Overview : DetailScreen("over_view")
}