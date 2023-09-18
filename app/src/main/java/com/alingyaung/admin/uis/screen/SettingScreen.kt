package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.uis.widget.NavItemWithText

@Composable
fun SettingScreen(
    navController: NavController
){
    val navList= remember {
        mutableStateOf(AppModule.navAddItems())
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        LazyColumn{
            items(navList.value){
                    NavItemWithText(data =it, onClickItem = {
                        navController.navigate("")
                    })
            }
        }
    }
}