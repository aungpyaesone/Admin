package com.alingyaung.admin.presentation.state

import com.alingyaung.admin.data.persistence.entity.Book

data class BookScreenState(
    var bookList: List<Book> = listOf()
): BaseState