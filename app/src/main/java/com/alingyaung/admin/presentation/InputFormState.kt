package com.alingyaung.admin.presentation

data class InputFormState(
    var name: String = "",
    var nameError: String? = null,
    var author: String = "",
    var authorError: String? = null,
    var isbn: String = "",
    var isbnError: String?= null,
    var category: String = "",
    var categoryError: String? = null,
    var price: Double = 0.0,
    var priceError: Double? = null,
    var stock: Int = 0,
    var stockError: Int? = null,
    var publication_date: String = "",
    var publication_dateError: String? = null,
    var publisher: String = "",
    var publisherError: String?= null,
    var imageUrl: String = "",
    var languageError: String? = null,
    var description: String = "",
    var descriptionError: String? = null,
)
