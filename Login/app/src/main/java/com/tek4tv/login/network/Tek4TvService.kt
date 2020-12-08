package com.tek4tv.login.network

import com.tek4tv.login.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Tek4TvService {
    @Headers("Content-Type: application/json")
    @POST("/api/token")
    suspend fun getToken(@Body body: Map<String, String>): String

    @Headers("Content-Type: application/json")
    @POST("/iot/v1/app/login")
    suspend fun login(@Body body: Map<String, String>, @Header("Authorization") token: String): Response<User>
}