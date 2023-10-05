package com.alingyaung.admin.data

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.utils.Resource
import com.alingyaung.admin.utils.Status
import com.alingyaung.admin.utils.checkForInternetConnection
import com.alingyaung.admin.utils.safeResume
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkModelImpl @Inject constructor(
    private val context: Context
) : FireBaseApi {

    val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override suspend fun insertAuthor(author: Author?): Resource<String> {
        return suspendCancellableCoroutine { continuation ->
            if (checkForInternetConnection(context)) { // Check if the device is connected to the internet
                try {
                    val authorMap = hashMapOf(
                        "id" to author?.id,
                        "name" to author?.name,
                        "bio" to author?.bio,
                        "image" to author?.image
                    )
                    author?.let {
                        db.collection("authors")
                            .document(it.id)
                            .set(authorMap)
                            .addOnSuccessListener {
                                Log.d("success", "Successfully add Author")
                                continuation.resume(Resource(Status.SUCCESS, "success", null))
                            }
                            .addOnFailureListener { e ->
                                Log.d("onFailure", "Failed to add Author")
                                continuation.safeResume(
                                    Resource(Status.ERROR, null, e.localizedMessage)
                                ){}
                            }
                    }
                } catch (e: Exception) {
                    Log.e("Error", "Firestore operation failed due to: $e")
                    continuation.safeResume(
                        Resource(Status.ERROR, null, "Failed to perform Firestore operation")
                    ){}
                }
            } else {
                // No internet connection, return error immediately
                continuation.safeResume(
                    Resource(Status.ERROR, null, "No internet connection")
                ){}
            }
        }
    }

    override suspend fun getAllAuthors(): List<Author> {
        return suspendCancellableCoroutine { continuation ->
                db.collection("authors")
                .addSnapshotListener { value, error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val authorList: MutableList<Author> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data
                            data?.put("id", document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson<Author>(gson, Author::class.java)
                            authorList.add(dataList)
                        }
                        continuation.safeResume(authorList) {
                            Log.d("job", "already done")
                        }
                    }
                }
        }
    }

    override suspend fun getAllCategory(): List<Category> {
        return suspendCancellableCoroutine { continuation ->
            db.collection("category")
                .addSnapshotListener { value, error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val categoryList: MutableList<Category> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data
                            data?.put("id", document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson, Category::class.java)
                            categoryList.add(dataList)
                        }
                        continuation.safeResume(categoryList){}
                    }
                }
        }
    }

    override suspend fun uploadImage(image: Bitmap): String {
        return suspendCancellableCoroutine { continuation ->
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val imageRef = storageReference.child("images/${UUID.randomUUID()}")
            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                //continuation?.resume(it.localizedMessage)
            }.addOnSuccessListener {
                Log.d("upload success", "success upload image")
                //  Log.d(ContentValues.TAG, "User profile updated.")
            }
            uploadTask.continueWithTask {
                return@continueWithTask imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                val imageUrl = task.result?.toString()
                imageUrl?.let {
                    continuation.safeResume(it){

                    }
                }
            }
        }
    }

    override suspend fun addBooks(book: Book?): Resource<String> {
        return suspendCancellableCoroutine { continuation ->
            if (checkForInternetConnection(context)) {
                try {
                    val bookMap = hashMapOf(
                        "id" to book?.id,
                        "name" to book?.name,
                        "isbn" to book?.isbn,
                        "image" to book?.image,
                        "author_id" to book?.author_id,
                        "category_id" to book?.category_id,
                        "price" to book?.price,
                        "publisher_id" to book?.publisher_id,
                        "description" to book?.description,
                        "publication_date" to book?.publication_date,
                        "genre_id" to book?.genre_id,
                        "stock" to book?.stock,
                        "created_date" to book?.created_date
                    )
                    book?.let {
                        db.collection("books")
                            .document(it.id)
                            .set(bookMap)
                            .addOnSuccessListener {
                                continuation.safeResume(Resource(Status.SUCCESS, "success", null)){}
                            }
                            .addOnFailureListener {
                                continuation.safeResume(Resource(Status.ERROR, null, it.localizedMessage)){}
                            }
                    }
                } catch (e:Exception){
                    continuation.safeResume(Resource(Status.ERROR, null, e.localizedMessage)){}
                }

        } else{
                continuation.safeResume(Resource(Status.ERROR, null, "No internet connection")){}
            }
    }
    }

    override suspend fun getAllBooks(): List<Book> {
        return suspendCancellableCoroutine { continuation ->
            db.collection("books")
                .addSnapshotListener { value, error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val bookList: MutableList<Book> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data
                            data?.put("id", document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson, Book::class.java)
                            bookList.add(dataList)
                        }
                        continuation.safeResume(bookList){

                        }
                    }
                }
        }
    }

    override suspend fun addGenre(genre: Genre?): kotlin.String {
        return suspendCancellableCoroutine { continuation ->
            val bookMap = hashMapOf(
                "id" to genre?.id,
                "name" to genre?.name,
            )
            genre?.let {
                db.collection("genres")
                    .document(it.id ?: UUID.randomUUID().toString())
                    .set(bookMap)
                    .addOnSuccessListener {
                        continuation.resume("Successfully Added Book")
                    }
                    .addOnFailureListener {
                        continuation.resume("Failed to Added Book")
                    }
            }

        }
    }

    override suspend fun insertCategory(category: Category?): Resource<String> {
        return suspendCancellableCoroutine { continuation ->
            if (checkForInternetConnection(context)) {
                try {
                    val bookMap = hashMapOf(
                        "id" to category?.id,
                        "name" to category?.name,
                    )
                    category?.let {
                        db.collection("category")
                            .document(it.id ?: UUID.randomUUID().toString())
                            .set(bookMap)
                            .addOnSuccessListener {
                                continuation.resume(Resource.success("success"))
                            }
                            .addOnFailureListener {
                                continuation.resume(
                                    Resource.error(
                                        it.localizedMessage ?: "failed",
                                        data = null
                                    )
                                )
                            }
                    }
                }catch (e:Exception){
                    continuation.safeResume(
                        Resource(Status.ERROR, null, e.localizedMessage)
                    ){}
                }
            } else {
                continuation.safeResume(
                    Resource(Status.ERROR, null, "No internet connection")
                ){}
            }
        }
    }

    override suspend fun addPublisher(publisher: Publisher?): Resource<String> {
        return suspendCancellableCoroutine { continuation ->
            if(checkForInternetConnection(context)) {
                val bookMap = hashMapOf(
                    "id" to publisher?.id,
                    "name" to publisher?.name,
                )
                publisher?.let {
                    db.collection("publisher")
                        .document(it.id ?: UUID.randomUUID().toString())
                        .set(bookMap)
                        .addOnSuccessListener {
                            continuation.resume(Resource.success("success"))
                        }
                        .addOnFailureListener {
                            continuation.resume(
                                Resource.error(
                                    it.localizedMessage ?: "failed",
                                    data = null
                                )
                            )
                        }
                }
            }else{
                continuation.safeResume(
                    Resource(Status.ERROR, null, "No internet connection")
                ){}
            }
        }
    }

    override suspend fun getAllGenres(): List<Genre> {
        return suspendCancellableCoroutine { continuation ->
            db.collection("genres")
                .addSnapshotListener { value, error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val genreList: MutableList<Genre> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data
                            data?.put("id", document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson, Genre::class.java)
                            genreList.add(dataList)
                        }
                        continuation.safeResume(genreList){}
                    }
                }
        }
    }

    override suspend fun getAllPublisher(): List<Publisher> {
        return suspendCancellableCoroutine { continuation ->
            db.collection("publisher")
                .addSnapshotListener { value, error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val publisherList: MutableList<Publisher> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data
                            data?.put("id", document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson, Publisher::class.java)
                            publisherList.add(dataList)
                        }
                        continuation.safeResume(publisherList){}

                    }
                }
        }
    }

}