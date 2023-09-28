package com.alingyaung.admin.data.remote

import android.graphics.Bitmap
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.utils.Resource

interface FireBaseApi {
    suspend fun insertAuthor(author: Author?): Resource<String>

    suspend fun getAllAuthors() : List<Author>

    suspend fun getAllCategory(): List<Category>

    suspend fun uploadImage(image: Bitmap):String

    suspend fun addBooks(book: Book?) :Resource<String>

    suspend fun getAllBooks() : List<Book>

    suspend fun addGenre(genre: Genre?) :String
    suspend fun insertCategory(category: Category?) : Resource<String>
    suspend fun addPublisher(publisher: Publisher?) :Resource<String>

    suspend fun getAllGenres() : List<Genre>

    suspend fun getAllPublisher() : List<Publisher>

}