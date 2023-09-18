package com.alingyaung.admin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun checkForInternetConnection(context: Context): Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        val activeNetWork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetWork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
    return connectivityManager.activeNetworkInfo?.isAvailable ?: false
}