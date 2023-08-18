package com.alingyaung.admin.di

import com.alingyaung.admin.data.remote.ALinApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerApi() : ALinApi{
        return Retrofit.Builder()
            .baseUrl(ALinApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create()
    }
}