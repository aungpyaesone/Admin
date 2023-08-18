package com.alingyaung.admin.data.remote

import com.alingyaung.admin.domain.Author
import retrofit2.http.GET

interface ALinApi {

    companion object{
        const val BASE_URL = ""
    }

    @GET("")
    suspend fun getAuthors(): List<Author>{
        return listOf()
    }
}