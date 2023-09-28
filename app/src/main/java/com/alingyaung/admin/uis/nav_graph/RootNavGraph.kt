package com.alingyaung.admin.uis.nav_graph

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.uis.screen.HomeScreen

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "detail_graph"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavRootGraph(navHostController: NavHostController,context: Context) {
    NavHost(
        navController = navHostController,
        startDestination = Graph.HOME,
        route = Graph.ROOT
    ) {
        composable(route = Graph.HOME){
            HomeScreen(rememberNavController())
        }
    }
}