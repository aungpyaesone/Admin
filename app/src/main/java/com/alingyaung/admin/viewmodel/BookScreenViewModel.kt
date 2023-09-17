package com.alingyaung.admin.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel(){

    private val _state = mutableStateOf(BookScreenState())
    val state: State<BookScreenState> = _state


    fun onEvent(event: BookScreenEvent){
        when(event) {
            is BookScreenEvent.GetBookListEvent ->{}
            is BookScreenEvent.BookFilterEvent -> {}
        }
    }
}