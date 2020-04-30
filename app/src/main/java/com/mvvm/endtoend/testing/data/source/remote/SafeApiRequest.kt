package com.mvvm.endtoend.testing.data.source.remote

import retrofit2.Response
import java.io.IOException
import com.mvvm.endtoend.testing.data.Result
import com.mvvm.endtoend.testing.data.Result.Success
import com.mvvm.endtoend.testing.data.Result.Error

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful){
            return Success(response.body()!!)
        } else {
            return Error(response.code().toString(), response.message().toString())
        }
    }
}

class ApiException(message: String) : IOException(message)