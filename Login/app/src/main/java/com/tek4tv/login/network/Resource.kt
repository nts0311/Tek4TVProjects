package com.tek4tv.login.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}

suspend fun <T> performNetworkCall(call: suspend () -> Response<T>): Resource<T> {
    return withContext(Dispatchers.IO)
    {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return@withContext Resource.Success(body)
            }
            return@withContext Resource.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return@withContext Resource.Error(e.message ?: e.toString())
        }
    }
}