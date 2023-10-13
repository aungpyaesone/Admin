package com.alingyaung.admin.uis.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.uis.widget.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel(){

    private val _state = mutableStateOf(BookScreenState())
    val state: State<BookScreenState> = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _books = MutableStateFlow(state.value.bookList)
    val books = searchTextState.debounce(1000L)
        .onEach { _isLoading.update { true } }
        .combine(_books){ text, books ->
            if(text.isBlank()){
                books
            }
            else{
                delay(2000L)
                books.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isLoading.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),_books.value)


    fun onEvent(event: BookScreenEvent){
        when(event) {
            is BookScreenEvent.GetBookListEvent ->{
                getBookList()
            }
            is BookScreenEvent.BookFilterEvent -> {
                onSearchTextChange(event.keyword)
            }
            is BookScreenEvent.UpdateSearchEvent -> updateSearchTextState(event.query)
            is BookScreenEvent.UpdatedWidgetState -> updateSearchWidgetState(event.searchWidgetState)
        }
    }

    private fun onSearchTextChange(text:String){
        _searchTextState.value = text
    }

    private fun getBookList() {
        viewModelScope.launch {
            _isLoading.update { true }
            repository.getAllBooks().collect {
                _state.value = _state.value.copy(bookList  = it.data ?: emptyList())
                _books.value = _state.value.bookList
                _isLoading.update { false }
            }
        }
    }
}