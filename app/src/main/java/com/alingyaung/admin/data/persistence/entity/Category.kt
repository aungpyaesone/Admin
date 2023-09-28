package com.alingyaung.admin.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alingyaung.admin.domain.DomainItem

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = false)
     override var id: String = "",
     override var name: String = "",
    var isSync : Boolean = false
): DomainItem{
    override fun doMatchSearchQuery(query: String): Boolean {
        TODO("Not yet implemented")
    }

}