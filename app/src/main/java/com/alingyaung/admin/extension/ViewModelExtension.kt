package com.alingyaung.admin.extension

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alingyaung.admin.repository.MainRepository
import com.alingyaung.admin.viewmodel.AuthorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import java.io.File
import java.io.IOException
import javax.inject.Inject

suspend fun compressImage(context: Context, file: File): String{
    val compressedImage = try {
        Compressor.compress(context,file) {
            quality(60) // Adjust the quality value as per your requirement (0 to 100)
            format(Bitmap.CompressFormat.JPEG) // Choose the desired image format
        }
    } catch (e: IOException) {
        // Handle the exception, such as showing an error message
        e.printStackTrace()
        file // Return the original file in case of compression failure
    }
    return compressedImage.absolutePath
}

// ViewModelFactory.kt

