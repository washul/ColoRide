package com.wsl.utils

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import com.wsl.utils.Result

inline fun <T> request(
    call: () -> Response<T>,
    default: T
): Result<Failure, T> {
    return try {
        val response = call.invoke()

        when (response.isSuccessful) {
            true -> Result.Success(response.body() ?: default)
            false -> Result.Failure(Failure.ServerError(HttpException(response)))
                .also { Log.w("Network", "Unsuccessful response: $response") }
        }
    } catch (exception: Throwable) {
        Log.e("Network", exception.toString())
        Result.Failure(
            Failure.ServerError(exception)
        )
    }
}