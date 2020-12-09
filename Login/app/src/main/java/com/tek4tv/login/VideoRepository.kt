package com.tek4tv.login

import com.tek4tv.login.network.VideosResponse
import com.tek4tv.login.network.VideosService
import retrofit2.Response
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videosService: VideosService) {

    suspend fun getVideos(siteMapID : Int, token : String) : Response<VideosResponse>
    {
        val body = mapOf("SiteMapID" to siteMapID.toString())
        return videosService.getVideo(body, "Bearer ".plus(token))
    }
}