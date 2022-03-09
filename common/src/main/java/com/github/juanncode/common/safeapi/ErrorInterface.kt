package com.github.juanncode.common.safeapi

interface ErrorInterface {
    fun getMessage(): String?
    fun getError(): String?
    fun getCode(): Int?
}