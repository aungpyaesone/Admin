package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.uis.widget.BookItemWidget

@Composable
fun BookListScreen(
    navHostController: NavHostController,
    state: BookScreenState,
    onEvent : (BookScreenEvent) -> Unit,
    onItemClick : (Book) -> Unit,
    isLoading: Boolean = false
){

    LaunchedEffect(true){
        onEvent(BookScreenEvent.GetBookListEvent)
    }

    val filterList = listOf("For You","Trending","New Releases")
    Column {
        if(isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }else{
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                // Child views.
                /* filterList.forEach { item ->
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
                 }*/

            }

            Box (modifier = Modifier.fillMaxSize()) {
                if (state.bookList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Empty Data")
                    }
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        columns = GridCells.Fixed(3),
                        content ={
                            this.items(state.bookList,key = {
                                it.id
                            }){data ->
                                BookItemWidget(
                                    book = data,
                                    onEvent = {},
                                    modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                    onItemClick = {
                                        onItemClick(data)
                                    }
                                )
                            }
                        })
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
        onEvent = {},
        onItemClick = {}
    )
}