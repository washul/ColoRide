package com.wsl.utils

import android.util.Log


sealed class Failure {
    object NetworkConnection : Failure()
    data class ServerError(val throws: Throwable) : Failure()
    data class CustomError(val errorMessage: String) : Failure()
    object NoContent : Failure()

    fun logError() {
        when (this) {
            is ServerError -> Log.e(
                TAG,
                throws.message.toString()
            )
            is NoContent -> Log.e(TAG, "No content")
            is CustomError -> Log.e(TAG, "Custom Error: $errorMessage")

            else -> Log.e(
                TAG,
                UNKNOWN_ERROR
            )
        }
    }

    fun getError() = when (this) {
        is ServerError -> throws.message
        is CustomError -> errorMessage
        else -> UNKNOWN_ERROR
    }

    fun throws(): Nothing = throw when (this) {
        is ServerError -> throws
        is CustomError -> Exception(errorMessage)
        else -> Exception(UNKNOWN_ERROR)
    }

    companion object {
        val TAG = Failure::class.simpleName
        internal val UNKNOWN_ERROR: String = "Something went wrong"
    }

}