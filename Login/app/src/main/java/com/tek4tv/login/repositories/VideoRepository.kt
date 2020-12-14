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
    var videosMap = mutableMapOf<String, List<Video>>()

    suspend fun getPlaylists(token: String): Resource<List<PlaylistItem>> {
        return performNetworkCall { videosService.getPlaylists("Bearer ".plus(token)) }
    }

    suspend fun getPlaylistDetail(
        token: String,
        playlistId: String
    ): Resource<PlaylistDetailResponse> {
        return performNetworkCall { videosService.getPlaylistDetail("Bearer ".plus(token), playlistId) }
    }
}