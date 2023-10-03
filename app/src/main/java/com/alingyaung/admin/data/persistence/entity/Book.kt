package com.alingyaung.admin.data.persistence.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "book",
    foreignKeys = [
        ForeignKey(entity = Author::class,
            parentColumns = ["id"],
            childColumns = ["author_id"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Publisher::class,
            parentColumns = ["id"],
            childColumns = ["publisher_id"],
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["author_id"]),
        Index(value = ["category_id"]),
        Index(value = ["publisher_id"])
    ]
    )
data class Book(
  @PrimaryKey(autoGenerate = false)
    var id: String,
    var genre_id: String? = null,
    var name: String = "",
    var author_id: String = "",
    var isbn: String = "",
    var category_id: String = "",
    var price: Double?= null,
    var stock: Int? = null,
    var publication_date: Long?,
    var created_date: Long?,
    var publisher_id: String = "",
    var description: String = "",
    var image: String = "",
    var isSync : Boolean = false,
    var isFavourite: Boolean = false
)