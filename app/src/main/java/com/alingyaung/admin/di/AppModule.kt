package com.alingyaung.admin.di

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.TypeSpecimen
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.alingyaung.admin.R
import com.alingyaung.admin.data.NetworkModelImpl
import com.alingyaung.admin.data.persistence.db.ALinDatabase
import com.alingyaung.admin.data.remote.ALinApi
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.NavAddScreen
import com.alingyaung.admin.domain.NavigationItem
import com.alingyaung.admin.utils.AppConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        // Initialize any application-level dependencies or perform setup here.
        return context
    }

    @Provides
    @Singleton
    fun navigationItems() = listOf(
        NavigationItem(
            route = "Home",
            title = R.string.books,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavigationItem(
            route = "Profile",
            title = R.string.book_form,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
            badgeCount = 45
        ),
        NavigationItem(
            route = "Setting",
            title =R.string.other,
            selectedIcon = Icons.Filled.Book,
            unselectedIcon = Icons.Outlined.Book,
            hasNews = true,
        ),
    )

    @Provides
    @Singleton
    fun navAddItems(context: Context) = listOf(
        NavAddScreen(
            route = "AddAuthor",
            title = R.string.add_author,
            selectedIcon = Icons.Default.PersonAdd,
            hasNews = false,
            badgeCount = null,
            background = Color.Cyan
        ),
        NavAddScreen(
            route = "AddCategory",
            title = R.string.add_category,
            selectedIcon = Icons.Default.Category,
            hasNews = false,
            badgeCount = null,
            background = Color.Magenta
        ),
        NavAddScreen(
            route = "AddPublisher",
            title = R.string.add_publisher,
            selectedIcon = Icons.Default.Business,
            hasNews = false,
            badgeCount = null,
            background = Color.Yellow
        ),
        NavAddScreen(
            route = "AddGenre",
            title = R.string.add_genre,
            selectedIcon = Icons.Default.TypeSpecimen,
            hasNews = false,
            badgeCount = null,
            background = Color.DarkGray
        )
    )

    @Provides
    @Singleton
    fun provideFirebaseApi(context: Context): FireBaseApi{
        return NetworkModelImpl(context)
    }

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,ALinDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideAuthorDao(db:ALinDatabase) = db.authorDao()

    @Singleton
    @Provides
    fun provideBookDao(db:ALinDatabase) = db.bookDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db:ALinDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun providePublisherDao(db:ALinDatabase) = db.publisherDao()


}