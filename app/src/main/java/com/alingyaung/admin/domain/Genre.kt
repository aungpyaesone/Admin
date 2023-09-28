package com.alingyaung.admin.domain

import kotlin.String

data class Genre(
    override var id: String = "",
    override var name: String = ""
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        TODO("Not yet implemented")
    }
}
