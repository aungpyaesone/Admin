package com.alingyaung.admin.uis.nav_graph

import android.content.Context
import android.os.Build
import android.telecom.Call.Details
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.uis.screen.AuthorInputFormScreen
import com.alingyaung.admin.uis.screen.BookListScreen
import com.alingyaung.admin.uis.screen.InputFormScreen
import com.alingyaung.admin.uis.screen.SettingScreen
import com.alingyaung.admin.uis.viewmodel.AuthorViewModel
import com.alingyaung.admin.uis.viewmodel.BookScreenViewModel
import com.alingyaung.admin.uis.viewmodel.FormViewModel
import com.alingyaung.admin.uis.viewmodel.SettingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavGraph(navHostController: NavHostController,title:(String)->Unit) {
    var bookId = remember { mutableStateOf("") }
    var fav = remember { mutableStateOf(false) }
    NavHost(
        navController = navHostController,
        startDestination = "Home",
        route = Graph.HOME
    ) {
        composable(route = "Home") {
            val viewModel = hiltViewModel<BookScreenViewModel>()
            val searchWidgetState by viewModel.searchWidgetState
            val searchTextState by viewModel.searchTextState.collectAsState()
            val books by viewModel.books.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            BookListScreen(
                navHostController = navHostController,
                state = books,
                onEvent = viewModel::onEvent,
                isLoading = isLoading,
                onItemClick = {
                    bookId.value = it.id
                    fav.value = it.isFavourite
                    navHostController.navigate(
                        route = Graph.DETAILS
                    )
                },
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState
            )
        }

        composable(route = "Profile") {
            val viewModel = it.shareViewModel<FormViewModel>(navHostController)
            val authorViewModel = hiltViewModel<AuthorViewModel>()
            InputFormScreen(
                navController = navHostController,
                viewModel.state.value,
                authorViewModel.state.value,
                isLoading = viewModel.isLoading.value,
                searchText = authorViewModel.searchText,
                onEvent = viewModel::onEvent,
                onCommonEvent = authorViewModel::onEvent
            )
        }
        composable(route = "Setting") {
           val viewModel = hiltViewModel<SettingViewModel>()
           SettingScreen(
               navController = navHostController,
               dataList = AppModule.navAddItems(LocalContext.current),
               state = viewModel.state.value,
               isLoading = viewModel.isLoading.value,
               onEvent = viewModel::onEvent)
        }
        navDetailGraph(navHostController,title = title, bookId = bookId.value, isFav = fav.value)
    }

}

@Composable
inline infix fun <reified T :ViewModel> NavBackStackEntry.shareViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

