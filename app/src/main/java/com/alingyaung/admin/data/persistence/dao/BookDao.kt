package com.alingyaung.admin.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("select * from book order by publication_date desc")
    fun getAllBook(): Flow<List<Book>>

    @Query("select * from book where id = :id")
    suspend fun getBookById(id:String) : Book
}