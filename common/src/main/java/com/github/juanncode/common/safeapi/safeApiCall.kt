package com.github.juanncode.common.safeapi

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher= Dispatchers.IO, apiCall: suspend () -> T): Resource<T> {

    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when(throwable) {
                is HttpException -> {
                    val body = throwable.response()?.errorBody()
                    val code = throwable.code()

                    if (body != null) {
                        try {
                            Resource.Error(throwable, ErrorGeneric(code = code, message = throwable.message, error = throwable.message))
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Resource.Error(throwable, ErrorGeneric(code = code, message = throwable.message, error = throwable.message))
                        }

                    }
                    Resource.Error(throwable, ErrorGeneric(code = code, message = throwable.message, error = throwable.message))
                }
                is IOException -> Resource.NetworkError

                else -> {
                    Resource.Error(throwable, ErrorGeneric(code = 0, message = throwable.message, error = throwable.stackTraceToString()))
                }
            }
        }
    }
}

