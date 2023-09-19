package com.alingyaung.admin.uis.screen

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MergeType
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alingyaung.admin.R
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.presentation.event.CommonEvent
import com.alingyaung.admin.presentation.event.InputFormEvent
import com.alingyaung.admin.presentation.state.AuthorState
import com.alingyaung.admin.presentation.state.InputFormState
import com.alingyaung.admin.uis.widget.ListDialog
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Preview(showBackground = true)
@Composable
fun InputFormScreen(
    navController: NavHostController,
    state: InputFormState,
    state2: AuthorState,
    localContext: Context,
    searchText: StateFlow<String>,
    isLoading : Boolean,
    onEvent: (InputFormEvent) -> Unit,
    onCommonEvent: (CommonEvent) -> Unit
) {
    var showAuthorDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showPublisherDialog by remember { mutableStateOf(false) }
    var showGenreDialog by remember { mutableStateOf(false) }
    val calendarState = rememberUseCaseState()
    val calendarState2 = rememberUseCaseState()

    val selectedDates = remember { mutableStateOf<List<LocalDate>>(listOf()) }
    val disabledDates = listOf(
        LocalDate.now().minusDays(7),
        LocalDate.now().minusDays(12),
        LocalDate.now().plusDays(3),
    )

    CalendarDialog(
        state = rememberUseCaseState {  },
        selection = CalendarSelection.Date{date ->
            onEvent(InputFormEvent.PublicDateChange(date.toString()))
        },
        )

    CalendarDialog(
        state = calendarState2,
        selection = CalendarSelection.Date{date ->
            onEvent(InputFormEvent.IsbnChange(date.toString()))
        })
    Column {
        if(isLoading){
            CircularProgressIndicator()
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.name, onValueChange = {
                            onEvent(InputFormEvent.NameChange(it))
                        },
                        isError = state.nameError != null,
                        placeholder = {
                            Text(text = "Name")
                        }, label = {
                            Text(stringResource(id = R.string.name))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                keyboardController?.hide()
                                //  FocusRequester()
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.authorVO.name,
                        enabled = false,
                        onValueChange = {
                            onEvent(InputFormEvent.AuthorChange(it))
                        }, isError = state.authorError != null,
                        placeholder = {
                            Text(text = "Author")
                        },
                        label = {
                            Text(stringResource(id = R.string.author))
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                showAuthorDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Face, contentDescription = "select image")
                            }

                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.categoryVO.name,
                        onValueChange = { onEvent(InputFormEvent.CategoryChange(it)) },
                        isError = state.categoryError != null,
                        label = {
                            Text(stringResource(id = R.string.category))
                        },
                        enabled = false,
                        trailingIcon = {
                            IconButton(onClick = {
                                showCategoryDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Category, contentDescription = "select image")
                            }

                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.isbn,
                        onValueChange = {
                            onEvent(InputFormEvent.IsbnChange(it))
                        },
                        enabled = false,
                        label = {
                            Text(stringResource(id = R.string.isbn))
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                calendarState2.show()
                            }) {
                                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "select image")
                            }}
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.price.toString(),
                        onValueChange = { newValue ->
                            val doubleValue = newValue.toDoubleOrNull() ?: 0.0
                            onEvent(InputFormEvent.PriceChange(doubleValue))
                        },
                        isError = state.priceError != null,
                        label = {
                            Text(stringResource(id = R.string.price))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.stock.toString(),
                        onValueChange = { newValue ->
                            val intValue = newValue.toIntOrNull() ?: 0
                            onEvent(InputFormEvent.StockChange(intValue))
                        },
                        isError = state.stockError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(stringResource(id = R.string.stock))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.publication_date,
                        onValueChange = { onEvent(InputFormEvent.PublicDateChange(it)) },
                        label = { Text(stringResource(id = R.string.publication_date)) },
                        // enabled = false,
                        isError = state.publication_dateError != null,
                        trailingIcon = {
                            IconButton(onClick = {
                                calendarState.show()
                            }) {
                                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "select image")
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { onEvent(InputFormEvent.DescriptionChange(it)) },
                        label = {
                            Text(stringResource(id = R.string.description))
                        })

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.padding(32.dp, 0.dp, 32.dp, 0.dp),
                        value = state.imageUrl,
                        //   enabled = false,
                        onValueChange = {
                            onEvent(InputFormEvent.ImageChange(it))
                        },
                        label = {
                            Text(stringResource(id = R.string.image))
                        },
                        trailingIcon = {
                            if (state2.isLoading) {
                                CircularProgressIndicator()
                            } else {
                                IconButton(onClick = {
                                    TedImagePicker.with(localContext)
                                        .mediaType(MediaType.IMAGE)
                                        .start {
                                            //viewModel.onEvent(InputFormEvent.SendImageEvent(it))
                                            try {
                                                val inputStream =
                                                    localContext.contentResolver.openInputStream(it)
                                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                                onEvent(InputFormEvent.SendImageEvent(bitmap))
                                                inputStream?.close()
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Image,
                                        contentDescription = "select image"
                                    )
                                }
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.publisherVO.name,
                        label = { Text(text = "Publisher") },
                        isError = state.publisherError != null,
                        enabled = false,
                        onValueChange = { onEvent(InputFormEvent.PublisherChange(it))
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                showPublisherDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.Business, contentDescription = "select image")
                            }

                        }
                    )

                    OutlinedTextField(
                        value = state.genreVO.name,
                        label = { Text(text = "Genre") },
                        enabled = false,
                        onValueChange = { onEvent(InputFormEvent.GenderChange(it)) },
                        isError = state.genreError != null,
                        trailingIcon = {
                            IconButton(onClick = {
                                showGenreDialog = true
                            }) {
                                Icon(imageVector = Icons.Default.MergeType, contentDescription = "select image")
                            }

                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        onEvent(InputFormEvent.Submit)
                    }) {
                        Text(text = "Submit ")
                    }
                }
            }
        }
    }

        ListDialog(
            showDialog = showAuthorDialog,
            title = "Select Author",
            state =state2,
            searchText=searchText,
            event = CommonEvent.GetAuthorEvent,
            setShowDialog = { showAuthorDialog = it },
            onClickItem = { data ->
                onEvent(InputFormEvent.AuthorVOChange(data as Author))
            }) {
            when(it){
                is CommonEvent.GetAuthorEvent ->  onCommonEvent(it)
            }
        }

    ListDialog(
        showDialog = showCategoryDialog,
        title = "Select Category",
        state =state2 ,
        searchText=searchText,
        event = CommonEvent.GetCategoryEvent,
        setShowDialog = { showCategoryDialog = it },
        onClickItem = { data ->
            onEvent(InputFormEvent.CategoryVOChange(data as Category))
        }) {
        when(it){
            is CommonEvent.GetCategoryEvent ->  onCommonEvent(it)
        }
    }

    ListDialog(
        showDialog = showPublisherDialog,
        title = "Select Publisher",
        state =state2 ,
        searchText=searchText,
        event = CommonEvent.GetPublisherEvent ,
        setShowDialog = { showPublisherDialog = it },
        onClickItem = { data ->
            onEvent(InputFormEvent.PublisherVOChange(data as Publisher))
        }) {
        when(it){
            is CommonEvent.GetPublisherEvent ->  onCommonEvent(it)
        }
    }

    ListDialog(
        showDialog = showGenreDialog,
        title = "Select Genre",
        state =state2 ,
        searchText=searchText,
        event = CommonEvent.GetGenreEvent   ,
        setShowDialog = { showGenreDialog = it },
        onClickItem = { data ->
            onEvent(InputFormEvent.GenreVOChange(data as Genre))
        }) {
        when(it){
            is CommonEvent.GetGenreEvent ->  onCommonEvent(it)
        }
    }
}



/*   var item by remember {
       mutableStateOf(Item())
   }*/
