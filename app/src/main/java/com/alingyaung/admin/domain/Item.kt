package com.alingyaung.admin.domain

data class Item(
    var name: String = "",
    var author: String = "",
    var isbn: String = "",
    var category: String = "",
    var price: Double = 0.0,
    var stock: Int = 0,
    var publication_date: String = "",
    var publisher: String = "",
    var language: String = "",
    var description: String = "",
    var id: String = ""
)
