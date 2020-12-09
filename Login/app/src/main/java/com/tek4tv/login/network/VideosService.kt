package com.tek4tv.login.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface VideosService {
    @POST("iot/v1/media/publish")
    suspend fun getVideo(
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<VideosResponse>
}