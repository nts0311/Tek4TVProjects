package com.tek4tv.login.network

import com.tek4tv.login.model.PlaylistItem
import retrofit2.Response
import retrofit2.http.*

interface VideosService
{
    @POST("iot/v1/media/publish")
    suspend fun getVideo(
            @Body body: Map<String, String>,
            @Header("Authorization") token: String
    ): Response<VideosResponse>

    @GET("api/app/playlist/parent/2014")
    suspend fun getPlaylists(
            @Header("Authorization") token: String
    ): Response<List<PlaylistItem>>

    @GET("api/app/playlist/{playlistId}")
    suspend fun getPlaylistDetail(
        @Header("Authorization") token: String,
        @Path("playlistId") playlistId : String
    ): Response<PlaylistDetailResponse>
}