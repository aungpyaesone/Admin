package com.alingyaung.admin.uis.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.uis.widget.SearchWidgetState
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

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun onEvent(event: BookScreenEvent){
        when(event) {
            is BookScreenEvent.GetBookListEvent ->{
                getBookList()
            }
            is BookScreenEvent.BookFilterEvent -> {}
            is BookScreenEvent.UpdateSearchEvent -> updateSearchTextState(event.query)
            is BookScreenEvent.UpdatedWidgetState -> updateSearchWidgetState(event.searchWidgetState)
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