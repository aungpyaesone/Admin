package com.alingyaung.admin.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.alingyaung.admin.data.persistence.dao.AuthorDao
import com.alingyaung.admin.data.persistence.dao.BookDao
import com.alingyaung.admin.data.persistence.dao.CategoryDao
import com.alingyaung.admin.data.persistence.dao.PublisherDao
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.utils.Resource
import com.alingyaung.admin.utils.Status
import com.alingyaung.admin.utils.checkForInternetConnection
import com.alingyaung.admin.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: FireBaseApi,
    private val authorDao: AuthorDao,
    private val categoryDao: CategoryDao,
    private val bookDao: BookDao,
    private val publisherDao: PublisherDao,
    private val appContext: Context
) {
    private var currentAuthorList : List<Author>? = null
    private var currentCategoryList : List<Category>? = null
    private var currentPublisherList : List<Publisher>? = null
    private var currentBookList : List<Book>? = null

    suspend fun insertAuthor(author: Author):Flow<Status> = flow{
        val response = api.insertAuthor(author)
        when(response.status){
            Status.SUCCESS -> {
                authorDao.insertAuthor(author.apply {
                    isSync = true
                })
            }
            Status.ERROR ->{
                authorDao.insertAuthor(author.apply {
                    isSync = false
                })
            }
            Status.LOADING ->{}
        }
        emit(response.status)
    }

    suspend fun addGenre(genre: Genre): Flow<String> = flow {
        val result = api.addGenre(genre)
        emit(result)
    }

    suspend fun addPublisher(publisher: Publisher): Flow<Status> = flow {
        val result = api.addPublisher(publisher)
        when(result.status){
            Status.SUCCESS -> {
                publisherDao.insertPublisher(publisher.apply {
                    isSync = true
                })
            }
            Status.ERROR ->{
                publisherDao.insertPublisher(publisher.apply {
                    isSync = false
                })
            }
            Status.LOADING ->{}
        }
        emit(result.status)
    }

    suspend fun addCategory(category: Category): Flow<Status> = flow {
        val result = api.insertCategory(category)
        when(result.status){
            Status.SUCCESS -> {
                categoryDao.insertCategory(category.apply {
                    isSync = true
                })
            }
            Status.ERROR ->{
                categoryDao.insertCategory(category.apply {
                    isSync = false
                })
            }
            Status.LOADING ->{}
        }
        emit(result.status)
    }

    suspend fun getAllBooks(): Flow<Resource<List<Book>>> {
        return networkBoundResource(
            query = {
                bookDao.getAllBook()
            },
            fetch = {
                delay(2000)
                syncAllBook()
                currentBookList
            },
            saveFetchResult = {response ->
                response?.let {
                    insertBooks(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(appContext)
            }
        )
    }

    suspend fun getBookById(bookId:String) : Book = bookDao.getBookById(bookId)

    suspend fun getAllAuthors(): Flow<Resource<List<Author>>> {
        return networkBoundResource(
            query = { authorDao.getAllAuthor() },
            fetch = {
                delay(2000)
                syncAllAuthor()
                currentAuthorList
            },
            saveFetchResult = { response ->
                response?.let {
                    insertAuthors(it.onEach { author-> author.isSync = true })
                }
            },
            shouldFetch = {
                checkForInternetConnection(context = appContext)
            }
        )
    }

    suspend fun getAllCategoryList(): Flow<Resource<List<Category>>> {
        return networkBoundResource(
            query = { categoryDao.getAllCategory() },
            fetch = {
                delay(2000)
                syncAllCategory()
                currentCategoryList
            },
            saveFetchResult = {response ->
                response?.let {
                    insertCategory(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context = appContext)
            }
        )
    }

    suspend fun getAllPublisher(): Flow<Resource<List<Publisher>>> {
        return networkBoundResource(
            query = { publisherDao.getAllPublisher() },
            fetch = {
                delay(2000)
                syncAllPublisher()
                currentPublisherList
            },
            saveFetchResult = { response ->
                response?.let {
                    insertPublisher(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(appContext)
            }
        )
    }

    suspend fun getAllGenreList(): Flow<List<Genre>> = flow {
        val result = api.getAllGenres()
        emit(result)
    }

    suspend fun addBook(book: Book): Flow<Status> = flow {
        val result = api.addBooks(book)
        when(result.status){
            Status.SUCCESS -> {
                bookDao.insertBook(book.apply {
                    isSync = true
                })
            }
            Status.ERROR ->{
                bookDao.insertBook(book.apply {
                    isSync = false
                })
            }
            Status.LOADING ->{}
        }
         emit(result.status)
    }

    suspend fun uploadImage(bitMap: Bitmap): Flow<String> = flow {
        val result = api.uploadImage(bitMap)
        emit(result)
    }

    suspend fun insertAuthors(authorList: List<Author>) {
        authorList.forEach { authorDao.insertAuthor(it) }
    }

    suspend fun insertCategory(categoryList: List<Category>) {
        categoryList.forEach {
            categoryDao.insertCategory(it)
        }
    }

    suspend fun insertPublisher(publisherList: List<Publisher>) {
        publisherList.forEach {
            publisherDao.insertPublisher(it)
        }
    }

    suspend fun insertBooks(bookList: List<Book>) {
        bookList.forEach {
            bookDao.insertBook(it)
        }
    }

    private suspend fun syncAllAuthor(){
        val unSyncAuthors = authorDao.getAllUnSyncAuthor()
        unSyncAuthors.forEach { author -> insertAuthor(author) }
        currentAuthorList = api.getAllAuthors()
        currentAuthorList?.let {authors ->
            authorDao.deleteAllAuthors()
            insertAuthors(authors.onEach { it.isSync = true })
        }
    }

    private suspend fun syncAllCategory(){
        val unSyncAllCategory = categoryDao.getAllUnSyncCategory()
        unSyncAllCategory.forEach { addCategory(it) }
        currentCategoryList = api.getAllCategory()
        currentCategoryList?.let {categories ->
            categoryDao.deleteAllCategory()
            insertCategory(categories.onEach { it.isSync = true })
        }
    }

    private suspend fun syncAllPublisher(){
        val unSyncAllPublisher = publisherDao.getAllUnSyncPublisher()
        unSyncAllPublisher.forEach { addPublisher(it) }
        currentPublisherList = api.getAllPublisher()
        currentPublisherList?.let { publishers ->
            publisherDao.deleteAllPublisher()
            insertPublisher(publishers.onEach { it.isSync = true })
        }

    }

    private suspend fun syncAllBook(){
        val unSyncAllBook = bookDao.getAllUnSyncBook()
        unSyncAllBook.forEach { addBook(it) }
        currentBookList = api.getAllBooks()
        currentBookList?.let { books ->
            bookDao.deleteAllBook()
            insertBooks(books.onEach { it.isSync = true })
        }
    }



}