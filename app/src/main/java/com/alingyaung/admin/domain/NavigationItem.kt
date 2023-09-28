@file:OptIn(ExperimentalMaterialApi::class)
package com.alingyaung.admin.domain

import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import kotlin.String

data class NavigationItem(
    val route : String,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

data class NavAddScreen(
    val route : String,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val background: Color
){
/*    object NavAddAuthor : NavAddScreen(
        route = "AddAuthor",
        title = "Add Author",
        selectedIcon = Icons.Default.PersonAdd,
        hasNews = false,
        badgeCount = null
    )

    object AddCategory : NavAddScreen(
        route = "AddCategory",
        title = "Add Category",
        selectedIcon = Icons.Default.Category,
        hasNews = false,
        badgeCount = null
    )
    object AddPublisher : NavAddScreen(
        route = "AddPublisher",
        title = "Add Publisher",
        selectedIcon = Icons.Default.Business,
        hasNews = false,
        badgeCount = null
    )
    object AddBook: NavAddScreen(
        route = "AddGenre",
        title = "Add Genre",
        selectedIcon = Icons.Default.TypeSpecimen,
        hasNews = false,
        badgeCount = null
    )*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItemsList(
    navigationItems: List<NavigationItem>,
    onItemSelected: (Int) -> Unit
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        navigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(navigationItem.title)
                    selectedItemIndex = index
                },
                icon = {
                    BadgedBox(badge = {
                        if (navigationItem.badgeCount != null) {
                            Badge {
                                androidx.compose.material3.Text(text = navigationItem.badgeCount.toString())
                            }
                        } else if (navigationItem.hasNews) {
                            Badge()
                        }
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = if (index == selectedItemIndex) {
                                navigationItem.selectedIcon
                            } else navigationItem.unselectedIcon,
                            contentDescription = ""
                        )
                    }
                },
                label = {
                    androidx.compose.material3.Text(text = stringResource(navigationItem.title))
                }
            )
        }}
    }

