package com.alingyaung.admin.presentation.event

sealed class BookDetailEvent {

    data class GetBookByIdEvent(val bookId:String): BookDetailEvent()

    data class OnUpdateFavouriteEvent(val isFavourite: Boolean): BookDetailEvent()
}