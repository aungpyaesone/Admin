package com.alingyaung.admin.data.remote

import android.graphics.Bitmap
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.domain.Publisher

interface FireBaseApi {
    suspend fun addAuthor(author: Author?): String

    suspend fun getAllAuthors() : List<Author>

    suspend fun getAllCategory(): List<Category>

    suspend fun uploadImage(image: Bitmap):String

    suspend fun addBooks(book: Item?) :String

    suspend fun getAllBooks() : List<Item>

    suspend fun addGenre(genre: Genre?) :String
    suspend fun addCategory(category: Category?) :String
    suspend fun addPublisher(publisher: Publisher?) :String

    suspend fun getAllGenres() : List<Genre>

    suspend fun getAllPublisher() : List<Publisher>

}