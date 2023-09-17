package com.alingyaung.admin.nav_graph

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alingyaung.admin.screen.AuthorInputFormScreen
import com.alingyaung.admin.screen.AuthorListScreen
import com.alingyaung.admin.screen.AuthorScreen
import com.alingyaung.admin.screen.BookListScreen
import com.alingyaung.admin.screen.BookScreen
import com.alingyaung.admin.screen.HomeScreen
import com.alingyaung.admin.screen.InputFormScreen
import com.alingyaung.admin.screen.SettingScreen
import com.alingyaung.admin.viewmodel.AuthorViewModel
import com.alingyaung.admin.viewmodel.BookScreenViewModel
import com.alingyaung.admin.viewmodel.FormViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavGraph(navHostController: NavHostController,context: Context) {
    NavHost(
        navController = navHostController,
        startDestination = "Home",
        route = Graph.HOME
    ) {
        composable(route = "Home") {
            val viewModel = hiltViewModel<FormViewModel>()
            AuthorInputFormScreen(
                navController = navHostController,
                viewModel.state.value,
                onEvent = viewModel::onEvent,
                viewModel.isLoading.value
            )
        }

        composable(route = "Profile") {
            val viewModel = hiltViewModel<FormViewModel>()
            val authorViewModel = hiltViewModel<AuthorViewModel>()
            InputFormScreen(
                navController = navHostController,
                viewModel.state.value,
                authorViewModel.state.value,
                context,
                isLoading = viewModel.isLoading.value,
                searchText = authorViewModel.searchText,
                onEvent = viewModel::onEvent,
                onCommonEvent = authorViewModel::onEvent
            )
        }

        composable(route = "Setting") {
            val mViewModel = hiltViewModel<BookScreenViewModel>()
            BookListScreen(
                navHostController = navHostController,
                state = mViewModel.state.value,
                onEvent = mViewModel::onEvent
            )
        }
    }
}

@Composable
fun <T> NavBackStackEntry.shareViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}