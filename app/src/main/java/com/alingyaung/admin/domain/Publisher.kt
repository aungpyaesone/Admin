package com.alingyaung.admin.domain

data class Publisher(
    override var id: String,
    override var name: String
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        TODO("Not yet implemented")
    }
}
