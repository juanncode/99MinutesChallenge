package com.github.juanncode.common.safeapi

class ErrorGeneric(
    private val error: String? = null,
    private val message: String? = null,
    private val code: Int? = null
) : ErrorInterface {
    override fun getMessage(): String? {
        return message
    }

    override fun getError(): String? {
        return error
    }

    override fun getCode(): Int? {
        return code
    }
}