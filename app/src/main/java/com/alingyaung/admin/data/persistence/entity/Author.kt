package com.alingyaung.admin.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alingyaung.admin.domain.DomainItem

@Entity(tableName = "author")
data class Author(
    @PrimaryKey(autoGenerate = false)
    override val id: String = "",
    override val name: String,
    val bio: String?,
    val image: String?
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        TODO("Not yet implemented")
    }
}