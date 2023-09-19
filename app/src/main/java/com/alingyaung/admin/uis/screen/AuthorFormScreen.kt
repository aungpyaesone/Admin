package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alingyaung.admin.R
import com.alingyaung.admin.presentation.event.InputFormEvent
import com.alingyaung.admin.presentation.state.AuthorState
import com.alingyaung.admin.presentation.state.InputFormState
import com.alingyaung.admin.uis.viewmodel.FormViewModel

@ExperimentalMaterial3Api
@Composable
fun AuthorInputFormScreen(
    navController: NavHostController,
    state: InputFormState,
    onEvent: (InputFormEvent) ->Unit,
    isLoading: Boolean
) {
    Box(modifier =  Modifier.fillMaxSize()){
        if(isLoading)CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = state.name, onValueChange = {
                        onEvent(InputFormEvent.NameChange(it))
                    },
                    modifier= Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // keyboardController?.hide()
                            //  FocusRequester()
                        }
                    ),
                    placeholder = { Text(text = stringResource(id = R.string.author_name)) },
                    isError = state.nameError != null,
                    label = { Text(text = "Author Name") }
                )
                Row {
                    Button(
                        onClick = {
                          //  onEvent(InputFormEvent.SubmitAuthor)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }

                OutlinedTextField(
                    value = state.genre, onValueChange = {
                        onEvent(InputFormEvent.GenderChange(it))
                    },
                    modifier= Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // keyboardController?.hide()
                            //  FocusRequester()
                        }
                    ),
                    placeholder = { Text(text = stringResource(id = R.string.genre_name)) },
                    isError = state.genreError != null,
                )
                Row {
                    Button(
                        onClick = {
                            onEvent(InputFormEvent.SubmitGenre)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
                OutlinedTextField(
                    value = state.category, onValueChange = {
                        onEvent(InputFormEvent.CategoryChange(it))
                    },
                    modifier= Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // keyboardController?.hide()
                            //  FocusRequester()
                        }
                    ),
                    placeholder = { Text(text = stringResource(id = R.string.category_name)) },
                    isError = state.categoryError != null,
                )
                Row {
                    Button(
                        onClick = {
                          //  onEvent(InputFormEvent.SubmitCategory)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }

                OutlinedTextField(
                    value = state.publisher, onValueChange = {
                        onEvent(InputFormEvent.PublisherChange(it))
                    },
                    modifier= Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // keyboardController?.hide()
                            //  FocusRequester()
                        }
                    ),
                    placeholder = { Text(text = stringResource(id = R.string.publisher)) },
                    isError = state.publisherError != null,
                )
                Row {
                    Button(
                        onClick = {
                            //onEvent(InputFormEvent.SubmitPublisher)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }


                }
            }
        }
    }
