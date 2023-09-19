package com.alingyaung.admin.data.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alingyaung.admin.data.persistence.dao.AuthorDao
import com.alingyaung.admin.data.persistence.dao.BookDao
import com.alingyaung.admin.data.persistence.dao.CategoryDao
import com.alingyaung.admin.data.persistence.dao.PublisherDao
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.data.persistence.entity.Publisher

@Database(
    entities = [Book::class,Category::class,Author::class,Publisher::class],
    version = 1,
    exportSchema = false
)
abstract class ALinDatabase  : RoomDatabase(){

    abstract fun bookDao() : BookDao

    abstract fun authorDao() : AuthorDao

    abstract fun categoryDao() : CategoryDao
    abstract fun publisherDao() : PublisherDao

}