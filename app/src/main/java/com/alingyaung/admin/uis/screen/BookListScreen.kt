package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState

@Composable
fun BookListScreen(
    navHostController: NavHostController,
    state: BookScreenState,
    onEvent : (BookScreenEvent) -> Unit
){

    val filterList = listOf("For You","Trending","New Releases")
    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            // Child views.
            filterList.forEach { item ->
                Text(
                    text = item,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.14.sp,
                    )
                )
            }

        }

    Box (modifier = Modifier.fillMaxSize()) {
        if (state.bookList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Empty Data")
            }
        } else {
            LazyColumn {
                items(state.bookList) {
                    Text(text = it.name)
                }
            }
        }
    }
    }
    
}




@Preview(showSystemUi = true)
@Composable
fun BookListPreview(){
    BookListScreen(
        navHostController = rememberNavController(),
        state = BookScreenState(emptyList<Book>()),
        onEvent = {}
    )
}