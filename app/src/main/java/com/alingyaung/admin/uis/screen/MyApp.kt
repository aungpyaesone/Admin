package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.R
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.domain.NavigationItemsList
import com.alingyaung.admin.utils.AppConstants
import com.alingyaung.admin.uis.viewmodel.AuthorViewModel
import com.alingyaung.admin.uis.viewmodel.FormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: FormViewModel = hiltViewModel()
    val otherViewModel : AuthorViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navigationItems = AppModule.navigationItems()
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.title)) },
                    scrollBehavior = scrollBehavior
                )
            }, bottomBar = {
                NavigationItemsList(
                    navigationItems = navigationItems,
                    onItemSelected = {
                        val route = when(it){
                            context.getString(R.string.home)-> AppConstants.HOME
                            context.getString(R.string.chat)-> AppConstants.BOOK_INPUT
                            else -> AppConstants.AUTHOR_INPUT
                        }
                        navController.navigate(route){
                            popUpTo(route){
                                inclusive = true
                            }
                        }
                    }
                )
            }) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = AppConstants.HOME
            ) {
                composable(AppConstants.AUTHOR_INPUT) {
                  //  val formViewModel = it.shareViewModel<FormViewModel>(navController = navController)
               /*     AuthorInputFormScreen(
                        navController = navController,
                        viewModel) */
                }
                composable(AppConstants.BOOK_INPUT) {
                   // InputFormScreen(navController,viewModel,otherViewModel)
                }
                composable(AppConstants.HOME) {
                  /*  AuthorListScreen(navHostController = navController,
                        context,
                        otherViewModel,
                        otherViewModel.state.value)*/
                }
            }

        }

    }

}

@Composable
fun <T> NavBackStackEntry.shareViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}