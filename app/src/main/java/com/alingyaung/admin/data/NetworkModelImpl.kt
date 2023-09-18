package com.alingyaung.admin.data

import android.graphics.Bitmap
import android.util.Log
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.domain.Publisher
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkModelImpl : FireBaseApi{

    val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    override suspend fun addAuthor(
        author: Author?
    ): String {
        return suspendCoroutine {continuation ->
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
                        Log.d("success","Successfully add Author")
                        continuation.resume("Successfully Added")
                    }
                    .addOnFailureListener{Log.d("onFailure","Failed to add Author")
                        continuation.resume("Failed to Added")
                    }
            }

        }
    }

    override suspend fun getAllAuthors(): List<Author> {
        return suspendCoroutine { continuation ->
            db.collection("authors")
                .addSnapshotListener{value,error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val authorList : MutableList<Author> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for(document in result){
                            val data = document.data
                            data?.put("id",document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson<Author>(gson,Author::class.java)
                            authorList.add(dataList)
                        }
                        continuation.resume(authorList)
                    }
                }
        }
    }

    override suspend fun getAllCategory(): List<Category> {
        return suspendCoroutine { continuation ->
            db.collection("category")
                .addSnapshotListener{value,error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val categoryList : MutableList<Category> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for(document in result){
                            val data = document.data
                            data?.put("id",document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson,Category::class.java)
                            categoryList.add(dataList)
                        }
                        continuation.resume(categoryList)
                    }
                }
        }
    }

    override suspend fun uploadImage(image: Bitmap): String {
        return suspendCoroutine { continuation ->
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
                    continuation.resume(it)
                }
            }
        }
    }

    override suspend fun addBooks(book: Item?): kotlin.String {
        return suspendCoroutine {continuation ->
            val bookMap = hashMapOf(
                "id" to book?.id,
                "name" to book?.name,
                "isbn" to book?.isbn,
                "image" to book?.image,
                "author_id" to book?.author_id,
                "category_id" to book?.category_id,
                "price" to book?.price,
                "publisher" to book?.publisher,
                "description" to book?.description,
                "publication_date" to book?.publication_date
            )
            book?.let {
                db.collection("books")
                    .document(it.id)
                    .set(bookMap)
                    .addOnSuccessListener {
                        continuation.resume("Successfully Added Book")
                    }
                    .addOnFailureListener{
                        continuation.resume("Failed to Added Book")
                    }
            }

        }
    }

    override suspend fun getAllBooks(): List<Item> {
        return suspendCoroutine { continuation ->
            db.collection("books")
                .addSnapshotListener{value,error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val bookList : MutableList<Item> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for(document in result){
                            val data = document.data
                            data?.put("id",document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson<Item>(gson,Item::class.java)
                            bookList.add(dataList)
                        }
                        continuation.resume(bookList)
                    }
                }
        }
    }

    override suspend fun addGenre(genre: Genre?): kotlin.String {
        return suspendCoroutine {continuation ->
            val bookMap = hashMapOf(
                "id" to genre?.id,
                "name" to genre?.name,
            )
            genre?.let {
                db.collection("genres")
                    .document(it.id ?:  UUID.randomUUID().toString())
                    .set(bookMap)
                    .addOnSuccessListener {
                        continuation.resume("Successfully Added Book")
                    }
                    .addOnFailureListener{
                        continuation.resume("Failed to Added Book")
                    }
            }

        }
    }

    override suspend fun addCategory(category: Category?): String {
        return suspendCoroutine { continuation ->
            val bookMap = hashMapOf(
                "id" to category?.id,
                "name" to category?.name,
            )
            category?.let {
                db.collection("category")
                    .document(it.id ?:  UUID.randomUUID().toString())
                    .set(bookMap)
                    .addOnSuccessListener {
                        continuation.resume("Successfully Added Category")
                    }
                    .addOnFailureListener{
                        continuation.resume("Failed to Added Category")
                    }
            }
        }

    }

    override suspend fun addPublisher(publisher: Publisher?): String {
        return suspendCoroutine { continuation ->
            val bookMap = hashMapOf(
                "id" to publisher?.id,
                "name" to publisher?.name,
            )
            publisher?.let {
                db.collection("publisher")
                    .document(it.id ?:  UUID.randomUUID().toString())
                    .set(bookMap)
                    .addOnSuccessListener {
                        continuation.resume("Successfully Added Category")
                    }
                    .addOnFailureListener{
                        continuation.resume("Failed to Added Category")
                    }
            }
        }
    }

    override suspend fun getAllGenres(): List<Genre> {
        return suspendCoroutine { continuation ->
            db.collection("genres")
                .addSnapshotListener{value,error ->
                    error?.let {
                     //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val genreList : MutableList<Genre> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for(document in result){
                            val data = document.data
                            data?.put("id",document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson,Genre::class.java)
                            genreList.add(dataList)
                        }
                        continuation.resume(genreList)
                    }
                }
        }
    }

    override suspend fun getAllPublisher(): List<Publisher> {
        return suspendCoroutine { continuation ->
            db.collection("publisher")
                .addSnapshotListener{value,error ->
                    error?.let {
                        //  continuation.resumeWithException(it.localizedMessage)
                    } ?: run {
                        val publisherList : MutableList<Publisher> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for(document in result){
                            val data = document.data
                            data?.put("id",document.id)
                            val gson = Gson().toJson(data)
                            val dataList = Gson().fromJson(gson,Publisher::class.java)
                            publisherList.add(dataList)
                        }
                        continuation.resume(publisherList)

                    }
                }
        }
    }

}