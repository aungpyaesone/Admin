package com.alingyaung.admin.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.unit.dp
import com.alingyaung.admin.R
import com.alingyaung.admin.presentation.InputFormEvent
import com.alingyaung.admin.presentation.InputFormState
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Preview(showBackground = true)
@Composable
fun InputFormScreen(
    viewModel: FormViewModel,
    state: InputFormState
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 TopAppBar(title = { Text(text = stringResource(id = R.string.title))},
                 scrollBehavior = scrollBehavior)
        },
    ) { values ->
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(values)){
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val localContext = LocalContext.current
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.name, onValueChange = {
                        viewModel.onEvent(InputFormEvent.NameChange(it))
                    }, isError = state.nameError != null,
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
                    OutlinedTextField(value = state.author, onValueChange = {
                        viewModel.onEvent(InputFormEvent.AuthorChange(it))
                    }, isError = state.authorError != null,
                        placeholder = {
                            Text(text = "Author")
                        },
                        label = {
                            Text(stringResource(id = R.string.author))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.category,
                        onValueChange = { viewModel.onEvent(InputFormEvent.CategoryChange(it)) },
                        label = {
                            Text(stringResource(id = R.string.category))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.isbn, onValueChange = {
                        InputFormEvent.IsbnChange(it)
                    },
                        label = {
                            Text(stringResource(id = R.string.isbn))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.price.toString(), onValueChange = { newValue ->
                        val doubleValue = newValue.toDoubleOrNull() ?: 0.0
                        viewModel.onEvent(InputFormEvent.PriceChange(doubleValue))
                    },
                        label = {
                            Text(stringResource(id = R.string.price))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.stock.toString(), onValueChange = { newValue ->
                        val intValue = newValue.toIntOrNull() ?: 0
                        viewModel.onEvent(InputFormEvent.StockChange(intValue))
                    }, label = {
                        Text(stringResource(id = R.string.stock))
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = state.publication_date,
                        onValueChange = {
                            viewModel.onEvent(InputFormEvent.PublicDateChange(it))
                        },
                        label = {
                            Text(stringResource(id = R.string.publication_date))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { viewModel.onEvent(InputFormEvent.DescriptionChange(it)) },
                        label = {
                            Text(stringResource(id = R.string.description))
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.imageUrl,
                        onValueChange = { viewModel.onEvent(InputFormEvent.ImageChange(it)) },
                        label = {
                            Text(stringResource(id = R.string.image))
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                TedImagePicker.with(localContext)
                                    .mediaType(MediaType.IMAGE)
                                    .start {
                                        Toast.makeText(localContext, "Picked image: $it", Toast.LENGTH_SHORT).show()
                                    }
                            }) {
                                Icon(imageVector = Icons.Default.Face , contentDescription = "select image")
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {focusRequester.freeFocus()}),
                        modifier = Modifier.focusRequester(focusRequester = focusRequester)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.publisher,
                        onValueChange = { viewModel.onEvent(InputFormEvent.PublisherChange(it)) })
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        viewModel.onEvent(InputFormEvent.Submit)
                    }) {
                        Text(text = "Submit ")
                    }
                }
            }
        } 
        
    }
    /*   var item by remember {
           mutableStateOf(Item())
       }*/
    

}