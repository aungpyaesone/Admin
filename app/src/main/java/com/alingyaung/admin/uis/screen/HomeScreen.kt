package com.alingyaung.admin.uis.screen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.R
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.domain.NavigationItem
import com.alingyaung.admin.uis.nav_graph.DetailScreen
import com.alingyaung.admin.uis.nav_graph.Graph
import com.alingyaung.admin.uis.nav_graph.HomeNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollBehavior2 = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("current route",currentRoute.toString())
    val title = remember {
        mutableStateOf("Home")
    }
    Scaffold(
        topBar = {
            Log.d("routell",currentRoute.toString())
            when(currentRoute){
                DetailScreen.BookDetail.route -> TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        Card(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.medium_dimen))
                                .background(MaterialTheme.colorScheme.onTertiary)
                                .clickable {
                                    navController.popBackStack()
                                }
                            ,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_dimen)),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .padding(
                                        dimensionResource(id = R.dimen.small_dimen)
                                    )
                                    )
                        }

                    },
                    scrollBehavior = scrollBehavior2
                )
                else -> {
                    TopAppBar(
                        title = { Text(text = title.value) },
                        scrollBehavior = scrollBehavior,
                    )
                }
            }

        },
        bottomBar = {
            BottomBar(navController = navController, title = {
                title.value = it
            },navBackStackEntry)
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            HomeNavGraph(navHostController = navController,title = {title.value = it})
        }

    }
}

@Composable
fun BottomBar(navController: NavHostController,title: (String) -> Unit,
              navBackStackEntry: NavBackStackEntry?
              ) {
    val screens = AppModule.navigationItems()
    //val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomDestination = screens.any {
        it.route == currentDestination?.route
    }

    if (bottomDestination) {
        NavigationBar {
            screens.forEach { sc ->
                AddItem(
                    screen = sc,
                    currentDestination = currentDestination,
                    navController = navController,
                    title = title)
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
    title: (String) -> Unit
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
            title(screen.title)
            Log.d("click title",screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.selectedIcon,
                contentDescription = "Navigation Icon"
            )
        }

    )

}