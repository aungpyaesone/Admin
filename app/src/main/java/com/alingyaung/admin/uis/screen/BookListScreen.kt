package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState

@Composable
fun BookListScreen(
    navHostController: NavHostController,
    state: BookScreenState,
    onEvent : (BookScreenEvent) -> Unit
){
    Box (modifier = Modifier.fillMaxSize()){
        if(state.bookList.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = "Empty Data")
            }
        }else{
            LazyColumn{
                items(state.bookList){
                    Text(text = it.name)
                }
            }
        }
       
    }
    
}