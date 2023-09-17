package com.alingyaung.admin.domain

import kotlin.String

data class Item(
    override var name: String = "",
    var author_id: String = "",
    var isbn: String = "",
    var category_id: String = "",
    var price: Double?= null,
    var stock: Int? = null,
    var publication_date: String = "",
    var publisher: String = "",
    var description: String = "",
    var genre_id: String? = null,
    override var id: String = "",
    var image: String = ""
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        return false
    }
}
