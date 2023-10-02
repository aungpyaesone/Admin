package com.alingyaung.admin.presentation.state

import com.alingyaung.admin.data.persistence.entity.Book

data class BookDetailUIState(
    val book: Book? = null
)