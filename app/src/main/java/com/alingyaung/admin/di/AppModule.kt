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
import com.alingyaung.admin.data.NetworkModelImpl
import com.alingyaung.admin.data.remote.ALinApi
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.NavAddScreen
import com.alingyaung.admin.domain.NavigationItem
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
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        NavigationItem(
            route = "Profile",
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
            badgeCount = 45
        ),
        NavigationItem(
            route = "Setting",
            title = "Settings",
            selectedIcon = Icons.Filled.Book,
            unselectedIcon = Icons.Outlined.Book,
            hasNews = true,
        ),
    )

    @Provides
    @Singleton
    fun navAddItems() = listOf(
        NavAddScreen(
            route = "AddAuthor",
            title = "Add Author",
            selectedIcon = Icons.Default.PersonAdd,
            hasNews = false,
            badgeCount = null
        ),
        NavAddScreen(
            route = "AddCategory",
            title = "Add Category",
            selectedIcon = Icons.Default.Category,
            hasNews = false,
            badgeCount = null
        ),
        NavAddScreen(
            route = "AddPublisher",
            title = "Add Publisher",
            selectedIcon = Icons.Default.Business,
            hasNews = false,
            badgeCount = null
        ),
        NavAddScreen(
            route = "AddGenre",
            title = "Add Genre",
            selectedIcon = Icons.Default.TypeSpecimen,
            hasNews = false,
            badgeCount = null
        )
    )

    @Provides
    @Singleton
    fun provideFirebaseApi(): FireBaseApi{
        return NetworkModelImpl()
    }

}