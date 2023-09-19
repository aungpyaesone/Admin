package com.alingyaung.admin.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alingyaung.admin.domain.DomainItem

@Entity(tableName = "Publisher")
data class Publisher(
    @PrimaryKey(autoGenerate = false)
    override var id: String,
    override var name: String
): DomainItem {
    override fun doMatchSearchQuery(query: String): Boolean {
        TODO("Not yet implemented")
    }
}
