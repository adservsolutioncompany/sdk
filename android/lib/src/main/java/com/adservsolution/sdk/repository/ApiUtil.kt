package com.adservsolution.sdk.repository

import android.util.Log
import com.adservsolution.sdk.domain.model.Result
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> Response<T>
): T? {
    val result: Result<T> = safeApiResult(call)
    var data: T? = null

    when (result) {
        is Result.Success ->
            data = result.data
        is Result.Error -> {
            Log.e("ApiUtil", "Exception: ${result.exception}")
        }
    }

    return data
}

private suspend fun <T : Any> safeApiResult(
    call: suspend () -> Response<T>
): Result<T> {
    val response = call.invoke()
    if (response.isSuccessful) {
        return Result.Success(response.body())
    }
    return Result.Error(IOException("Error occurred during getting safe Api result"))
}
