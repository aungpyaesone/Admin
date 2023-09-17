package com.alingyaung.admin.presentation.state

import com.alingyaung.admin.domain.Item

class BookScreenState(
    var bookList: List<Item> = listOf()
): BaseState