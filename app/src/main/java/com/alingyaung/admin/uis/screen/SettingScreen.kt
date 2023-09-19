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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.di.AppModule
import com.alingyaung.admin.domain.NavAddScreen
import com.alingyaung.admin.presentation.event.CommonEvent
import com.alingyaung.admin.presentation.event.InputFormEvent
import com.alingyaung.admin.uis.widget.InputDialogWidget
import com.alingyaung.admin.uis.widget.NavItemWithText
import com.alingyaung.admin.utils.AppConstants.TYPE_AUTHOR
import com.alingyaung.admin.utils.AppConstants.TYPE_CATEGORY
import com.alingyaung.admin.utils.AppConstants.TYPE_GENRE
import com.alingyaung.admin.utils.AppConstants.TYPE_PUBLISHER

@Composable
fun SettingScreen(
    navController: NavController,
    dataList: List<NavAddScreen>,
    onEvent: (InputFormEvent)->Unit
){
    val navList= remember {
        mutableStateOf(AppModule.navAddItems())
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    var title by remember {
        mutableStateOf("")
    }

    var type by remember{
        mutableIntStateOf(0)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Fixed(2),
            content = {
                items(dataList){
                    NavItemWithText(
                        data =it,
                        onClickItem = {screen ->
                        showDialog = true
                        title = it.title
                        type = when(title){
                            "Add Author" -> TYPE_AUTHOR
                            "Add Category" -> TYPE_CATEGORY
                            "Add Publisher" -> TYPE_PUBLISHER
                            else -> TYPE_GENRE
                        }
                    })
                }
            }
        )
    }

    InputDialogWidget(
        showDialog = showDialog,
        title =  title,
        type = type,
        setShowDialog ={
            showDialog = false
        } ,
        submitValue = { value, jobType ->
            when(jobType){
                TYPE_AUTHOR -> {
                    onEvent(InputFormEvent.SubmitAuthor(value))
                }
                TYPE_CATEGORY->{
                    onEvent(InputFormEvent.SubmitAuthor(value))
                }
                TYPE_PUBLISHER ->{
                    onEvent(InputFormEvent.SubmitPublisher(value))
                }
                else ->{}
            }
        })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreview(){
    SettingScreen(
        rememberNavController(),
        AppModule.navAddItems(),{})
}