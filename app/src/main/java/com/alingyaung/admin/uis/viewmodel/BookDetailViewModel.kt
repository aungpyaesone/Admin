package com.alingyaung.admin.uis.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.presentation.event.BookDetailEvent
import com.alingyaung.admin.presentation.state.BookDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel() {

    private val _bookDetailUi = mutableStateOf(BookDetailUIState())
    val bookDetailUIState = _bookDetailUi

    fun onEvent(event:BookDetailEvent){
        when(event){
            is BookDetailEvent.GetBookByIdEvent ->{
                getBookById(event.bookId)
            }

            is BookDetailEvent.OnUpdateFavouriteEvent -> TODO()
        }
    }

    private fun getBookById(bookId: String) {
        viewModelScope.launch {
            val result = repository.getBookById(bookId)
            bookDetailUIState.value = bookDetailUIState.value.copy(book = result)
        }
    }

}