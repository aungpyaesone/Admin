package com.alingyaung.admin.presentation.state


import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.data.persistence.entity.Publisher

data class InputFormState(
    var name: String = "",
    var nameError: String? = null,
    var genre : String ="",
    var genreError: String? = null,
    var author: String = "",
    var authorError: String? = null,
    var isbn: String = "",
    var isbnError: String?= null,
    var category: String = "",
    var categoryError: String? = null,
    var price: Double = 0.0,
    var priceError: String? = null,
    var stock: Int = 0,
    var stockError: String? = null,
    var publication_date: String = "",
    var publication_dateError: String? = null,
    var publisher: String = "",
    var publisherError: String?= null,
    var imageUrl: String = "",
    var languageError: String? = null,
    var description: String = "",
    var descriptionError: String? = null,
    var authorList : MutableList<String> = mutableListOf(),
    var authorVO: Author = Author("","","",""),
    var categoryVO: Category = Category("",""),
    var genreVO : Genre = Genre("",""),
    var publisherVO: Publisher = Publisher("","")

): BaseState{
    fun addAuthor(author: String) {
        authorList.add(author)
    }
}
