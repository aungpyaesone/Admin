package com.alingyaung.admin.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alingyaung.admin.data.persistence.entity.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)
    @Query("select * from book order by publication_date desc")
    fun getAllBook(): Flow<List<Book>>

    @Query("select * from book where id = :id")
    suspend fun getBookById(id:String) : Book


    @Query("select * from book where isSync= :sync")
    suspend fun getAllUnSyncBook(sync:Boolean = false): List<Book>

    @Query("delete from book")
    suspend fun deleteAllBook()
}