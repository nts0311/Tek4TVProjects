package com.tek4tv.login.repositories

import android.util.Log
import com.tek4tv.login.model.PlaylistItem
import com.tek4tv.login.model.Video
import com.tek4tv.login.network.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(private val videosService: VideosService) {

    var allVideoList = listOf<Video>()
    var currentVideoList = listOf<Video>()

    suspend fun getVideos(
        siteMapID: Int,
        token: String,
        query: String = ""
    ): Response<VideosResponse>? {
        val body = mutableMapOf("SiteMapID" to siteMapID.toString())
        if (query != "")
            body["KeySearch"] = query

        return try {
            val response = videosService.getVideo(body, "Bearer ".plus(token))

            if (response.isSuccessful) {
                if (query == "")
                    allVideoList = response.body()!!.result

                currentVideoList = response.body()!!.result
            }
            response
        } catch (e: Exception) {
            Log.e("VideoRepo.getVideos", e.message ?: "")
            null
        }
    }

    suspend fun getPlaylists(body: PlaylistBody, token: String): Resource<List<PlaylistItem>> {
        return performNetworkCall { videosService.getPlaylists("Bearer ".plus(token)) }
    }

    suspend fun getPlaylistDetail(
        body: PlaylistBody,
        token: String,
        playlistId: String
    ): Resource<PlaylistDetailResponse> {
        return performNetworkCall { videosService.getPlaylistDetail("Bearer ".plus(token), playlistId) }
    }
}