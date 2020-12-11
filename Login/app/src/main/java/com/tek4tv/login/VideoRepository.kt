package com.tek4tv.login

import android.util.Log
import com.tek4tv.login.model.Video
import com.tek4tv.login.network.VideosResponse
import com.tek4tv.login.network.VideosService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(private val videosService: VideosService) {

    var videoList = listOf<Video>()

    suspend fun getVideos(siteMapID : Int, token : String) : Response<VideosResponse>?
    {
        val body = mapOf("SiteMapID" to siteMapID.toString())

        return try {
            val response = videosService.getVideo(body, "Bearer ".plus(token))

            if(response.isSuccessful)
                videoList = response.body()!!.result

            response
        }
        catch (e : Exception)
        {
            Log.e("VideoRepo.getVideos", e.message ?: "")
            null
        }
    }
}