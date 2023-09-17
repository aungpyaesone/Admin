package com.alingyaung.admin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdminApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}