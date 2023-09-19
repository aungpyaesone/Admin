package com.alingyaung.admin.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.data.persistence.entity.Publisher
import kotlinx.coroutines.flow.Flow

@Dao
interface PublisherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublisher(publisher: Publisher)
    @Query("select * from publisher")
    fun getAllPublisher(): Flow<List<Publisher>>

    @Query("select * from publisher where id = :id")
    suspend fun getPublisherById(id:String) : Publisher
}