package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.domain.NavAddScreen
import com.alingyaung.admin.uis.widget.NavItemWithText

@Composable
fun SettingScreen(
    navController: NavController,
    dataList: List<NavAddScreen>
){
    val navList= remember {
        mutableStateOf(AppModule.navAddItems())
    }
    Box(modifier = Modifier.fillMaxSize()
        .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(2),
            content = {
                items(dataList){
                    NavItemWithText(data =it, onClickItem = {screen ->
                        navController.navigate(screen.route)
                    })
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview(){
    SettingScreen(
        rememberNavController(),
        AppModule.navAddItems())
}