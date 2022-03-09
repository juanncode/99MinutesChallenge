package com.github.juanncode.common.safeapi

sealed class Resource<out T> {
    data class Success<T>(val value: T): Resource<T>()
    data class Error(val throwable: Throwable, val data: ErrorInterface? = null) : Resource<Nothing>()
    object NetworkError: Resource<Nothing>()


}