package com.alingyaung.admin.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = false)
     var id: String = "",
     var name: String
)