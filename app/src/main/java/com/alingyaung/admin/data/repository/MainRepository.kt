package com.alingyaung.admin.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.alingyaung.admin.data.persistence.dao.AuthorDao
import com.alingyaung.admin.data.persistence.dao.BookDao
import com.alingyaung.admin.data.persistence.dao.CategoryDao
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.domain.Publisher
import com.alingyaung.admin.utils.Resource
import com.alingyaung.admin.utils.checkForInternetConnection
import com.alingyaung.admin.utils.networkBoundResource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: FireBaseApi,
    private val authorDao: AuthorDao,
    private val categoryDao: CategoryDao,
    private val bookDao: BookDao,
    private val appContext: Context
) {
    suspend fun addAuthor(author: Author) : Flow<String> = flow{
         val result = api.addAuthor(author)
        emit(result)
    }


    suspend fun addGenre(genre: Genre): Flow<String> = flow {
        val result = api.addGenre(genre)
        emit(result)
    }

    suspend fun addPublisher(publisher: Publisher): Flow<String> = flow {
        val result = api.addPublisher(publisher)
        emit(result)
    }

    suspend fun addCategory(category: Category): Flow<String> = flow {
        val result = api.addCategory(category)
        emit(result)
    }

    suspend fun getAllBooks(): Flow<List<Item>> = flow {
        val result = api.getAllBooks()
        emit(result)
    }

    suspend fun getAllAuthors(): Flow<Resource<List<Author>>>{
        return networkBoundResource(
            query = {authorDao.getAllAuthor()},
            fetch = {
                    delay(500)
                    api.getAllAuthors()
            },
            saveFetchResult = { response ->
                              insertAuthors(response)
            },
            shouldFetch = {
                checkForInternetConnection(context = appContext)
            }
        )
    }

    suspend fun getAllCategoryList(): Flow<List<Category>> = flow {
        val result = api.getAllCategory()
        emit(result)
    }

    suspend fun getAllGenreList(): Flow<List<Genre>> = flow {
        val result = api.getAllGenres()
        emit(result)
    }


    suspend fun getAllPublisherList(): Flow<List<Publisher>> = flow {
        val result = api.getAllPublisher()
        emit(result)
    }

    suspend fun addBook(book: Item) : Flow<String> = flow{
        val result = api.addBooks(book)
        emit(result)
    }

    suspend fun uploadImage(bitMap:Bitmap) : Flow<String> = flow {
        val result = api.uploadImage(bitMap)
        emit(result)
    }

    suspend fun insertAuthors(authorList:List<Author>){
        authorList.forEach { authorDao.insertAuthor(it)}
    }
}