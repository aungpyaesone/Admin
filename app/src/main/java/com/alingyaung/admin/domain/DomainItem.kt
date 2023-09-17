package com.alingyaung.admin.domain

interface DomainItem {
    val id: String
    val name: String

    fun doMatchSearchQuery(query: String): Boolean
}