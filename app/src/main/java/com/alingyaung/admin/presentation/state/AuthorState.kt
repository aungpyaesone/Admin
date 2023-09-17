package com.alingyaung.admin.presentation.state

import com.alingyaung.admin.domain.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.DomainItem
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Publisher

data class AuthorState(
    var  authorList : List<Author>? = listOf(),
    var  categoryList : List<Category>? = listOf(),
    var  genreList : List<Genre>? = listOf(),
    var publisherList: List<Publisher>? = listOf(),
    var isLoading : Boolean = false
): BaseState
