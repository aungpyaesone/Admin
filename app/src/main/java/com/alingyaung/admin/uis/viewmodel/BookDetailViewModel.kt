package com.alingyaung.admin.uis.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.presentation.event.BookDetailEvent
import com.alingyaung.admin.presentation.state.BookDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

            is BookDetailEvent.OnUpdateFavouriteEvent -> updateFavourite(event.bookId,event.isFavourite)
        }
    }

    private fun updateFavourite(bookId: String,favourite: Boolean) {
        Log.d("isFavouritegg",favourite.toString())
        viewModelScope.launch{
            val book = repository.getBookById(bookId = bookId).apply {
                Log.d("isFavourite",this.isFavourite.toString())
                this.isFavourite = favourite
            }
            repository.updateBook(book)
        }
    }

    private fun getBookById(bookId: String) {
        viewModelScope.launch {
            val result = repository.getBookById(bookId)
            delay(100L)
            Log.d("result",result.isFavourite.toString())
            bookDetailUIState.value = bookDetailUIState.value.copy(book = result)
        }
    }

}