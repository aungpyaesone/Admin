package com.alingyaung.admin.utils

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume


data class Resource<out T>(val status:Status, val data:T?,val message:String?){
    companion object{
        fun <T> success(data:T?): Resource<T>{
            return Resource(Status.SUCCESS,data,null)
        }

        fun <T> error(message: String,data:T?): Resource<T>{
            return Resource(Status.ERROR,data,message)
        }

        fun <T> loading(data:T?): Resource<T>{
            return Resource(Status.LOADING,data,null)
        }
    }
}


enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}
inline fun<ResultType,RequestType> networkBoundResource(
    crossinline query: ()-> Flow<ResultType>,
    crossinline fetch: suspend() -> RequestType,
    crossinline saveFetchResult: suspend(RequestType) -> Unit,
    crossinline shouldFetch : (ResultType) -> Boolean = {true}
) = flow {
    // first step fetch data from the local cache
    val data = query().first()

    // If should fetch return true
    val resource = if(shouldFetch(data)){

        //Dispatch  a message to the UI that you are doing some background work
        emit(Resource.loading(data))

        try {
            // make a network call
            val resultType = fetch()

            // save it to the  database
            saveFetchResult(resultType)

            // Now fetch data again from the database and Dispatch it to UI
            query().map { Resource.success(it) }

        } catch (throwable: Throwable){
            //Dispatch any error emitted to the UI, plus data emmited from the Database
            query().map { Resource.error(throwable.message.toString(), it) }
        }
        // If should fetch return false
    } else{
        // Make a query to the database and Dispatch it to the UI
        query().map { Resource.success(it) }
    }

    emitAll(resource)

}

inline fun <T> Continuation<T>.safeResume(value: T, onExceptionCalled: () -> Unit) {
    if (this is CancellableContinuation) {
        if (isActive)
            resume(value)
        else
            onExceptionCalled()
    } else throw Exception("Must use suspendCancellableCoroutine instead of suspendCoroutine")
}