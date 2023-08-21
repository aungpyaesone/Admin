@file:OptIn(ExperimentalMaterialApi::class)

package com.alingyaung.admin.domain

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.alingyaung.admin.R

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItemsList(
    navigationItems: List<NavigationItem>,
    currentSelectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        navigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { selectedItemIndex = index },
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
                            contentDescription = navigationItem.title
                        )
                    }
                },
                label = {
                    androidx.compose.material3.Text(text = navigationItem.title)
                }
            )

        }
    }

    /* LazyColumn {
         items(navigationItems) { item ->
             NavigationItemRow(
                 item = item,
                 isSelected = item.title == currentSelectedItem,
                 onItemClick = { onItemSelected(item.title) }
             )
             Divider() // Optional divider between items
         }
     }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItemRow(
    item: NavigationItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        icon = {
            Icon(
                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.title
            )
        },
        text = {
            Text(text = item.title)
        },
        secondaryText = {
            if (item.hasNews) {
                BadgedBox(
                    badge = {
                        Badge(content = {
                            Text(text = item.badgeCount.toString())
                        })
                    }
                ) {
                    Text(text = "Settings")
                }
            }
        }
    )
}
