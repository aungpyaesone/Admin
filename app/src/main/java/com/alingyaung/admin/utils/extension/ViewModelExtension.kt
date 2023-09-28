package com.alingyaung.admin.utils.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.uis.viewmodel.AuthorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

suspend fun compressImage(context: Context, file: File): Bitmap{
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
    val compressBitmap  = withContext(Dispatchers.IO){
        BitmapFactory.decodeFile(compressedImage.absolutePath)
    }
    return compressBitmap
}

fun getFileFromUri(context: Context, uri: Uri): File? {
    var file: File? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    try {
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            if (!filePath.isNullOrEmpty()) {
                file = File(filePath)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return file
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            return BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

// ViewModelFactory.kt

