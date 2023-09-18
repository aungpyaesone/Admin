package com.alingyaung.admin.uis.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alingyaung.admin.domain.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.DomainItem
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Publisher
import com.alingyaung.admin.presentation.event.BaseEvent
import com.alingyaung.admin.presentation.event.CommonEvent
import com.alingyaung.admin.presentation.state.AuthorState
import com.alingyaung.admin.presentation.state.BaseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> ListDialog(
    showDialog: Boolean,
    title: String,
    event: T,
    searchText: StateFlow<String>,
    state: BaseState,
    onClickItem: (DomainItem) -> Unit,
    setShowDialog: (Boolean) -> Unit,
    onEvent: (BaseEvent) -> Unit
) {
    // Declare variables for domainList and categoryList
    val domainList: List<DomainItem>?
    val categoryList: List<Category>?
    val genreList: List<Genre>?
    val publisherList: List<Publisher>?

    // Smart cast to AuthorState if the state is of that type
    if (state is AuthorState) {
        domainList = state.authorList
        categoryList = state.categoryList
        genreList = state.genreList
        publisherList = state.publisherList

    } else {
        domainList = null
        categoryList = null
        genreList = null
        publisherList = null
    }

    val mSearchText by searchText.collectAsState()
    //  val mSearching by isSearching.collectAsState()


    LaunchedEffect(event) {
        when (event) {
            is CommonEvent.GetAuthorEvent -> {
                onEvent(CommonEvent.GetAuthorEvent)
            }

            is CommonEvent.GetCategoryEvent -> {
                onEvent(CommonEvent.GetCategoryEvent)
            }

            is CommonEvent.GetGenreEvent -> {
                onEvent(CommonEvent.GetGenreEvent)
            }

            is CommonEvent.GetPublisherEvent -> {
                onEvent(CommonEvent.GetPublisherEvent)
            }
        }
    }
    val listItem: List<DomainItem> = when (event) {
        is CommonEvent.GetAuthorEvent -> {
            domainList ?: emptyList()
        }

        is CommonEvent.GetCategoryEvent -> {
            categoryList ?: emptyList()
        }

        is CommonEvent.GetGenreEvent -> {
            genreList ?: emptyList()
        }

        is CommonEvent.GetPublisherEvent -> {
            publisherList ?: emptyList()
        }

        else -> {
            emptyList<DomainItem>()
        }


    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { setShowDialog(false) },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Set the width of the dialog
                        .height(600.dp) // Set the height of the dialog
                        .background(color = Color.White)
                    // Set the background color of the dialog
                ) {
                    Column {


                       /* TextField(
                            value = mSearchText,
                            onValueChange = {
                                Log.d("searchText", it)
                                onEvent(CommonEvent.SearchTextEvent(it))
                            })*/

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = title,
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    color = Color.Black
                                )
                                Icon(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            setShowDialog(false)
                                        }, imageVector = Icons.Default.Close, contentDescription = null
                                )
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(listItem) { item ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    ItemsWidget(
                                        item = item,
                                        onClick = { clickItem ->
                                            onClickItem(clickItem)
                                            /*when(item){
                                                is Author -> {
                                                    onClickItem(item)
                                                }
                                                is Category -> {
                                                    onClickItem(item)
                                                }
                                                is Genre -> {

                                                }
                                            }*/
                                            setShowDialog(false)
                                        })
                                }

                            }
                        }
                    }

                }
            }
        }
    }
}

