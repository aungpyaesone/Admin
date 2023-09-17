package com.alingyaung.admin.domain

import kotlin.String

data class Author(
    override val id: String,
    override val name: String,
    val bio: String?,
    val image: String?
) : DomainItem{

    override fun doMatchSearchQuery(query: String): Boolean{
        val matchCombinations = listOf(name)
        return matchCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
