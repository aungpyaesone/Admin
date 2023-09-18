package com.alingyaung.admin.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alingyaung.admin.data.persistence.entity.Author
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: Author)
    @Query("select * from  author")
    fun getAllAuthor() : Flow<List<Author>>

    @Query("select * from author where id = :id")
    suspend fun getAuthorById(id:String) : Author

}