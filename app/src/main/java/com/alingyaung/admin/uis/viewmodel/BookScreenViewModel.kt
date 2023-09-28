package com.alingyaung.admin.uis.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel(){

    private val _state = mutableStateOf(BookScreenState())
    val state: State<BookScreenState> = _state

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading


    fun onEvent(event: BookScreenEvent){
        when(event) {
            is BookScreenEvent.GetBookListEvent ->{
                getBookList()
            }
            is BookScreenEvent.BookFilterEvent -> {}
        }
    }

    private fun getBookList() {
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllBooks().collect {
                _state.value = _state.value.copy(bookList  = it.data ?: emptyList())
                _isLoading.value = false
            }
        }
    }
}