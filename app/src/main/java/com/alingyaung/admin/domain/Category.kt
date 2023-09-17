package com.alingyaung.admin.domain

data class Category(
    override var id: String,
    override var name: String
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        return false
    }
}