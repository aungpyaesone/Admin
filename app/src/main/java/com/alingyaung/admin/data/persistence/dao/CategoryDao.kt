package com.alingyaung.admin.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("select * from category")
    fun getAllCategory(): Flow<List<Category>>

    @Query("select * from category where id = :id")
    suspend fun getCategoryById(id:String) : Category
}