package com.tek4tv.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tek4tv.login.repositories.VideoRepository
import com.tek4tv.login.model.Video

class VideoPlayerViewModel @ViewModelInject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {
    var currentWindow = 0
    var playbackPosition: Long = 0
    var curVideo: Video? = null
    var playlistId: String = ""
    

    fun resetVideoParams() {
        currentWindow = 0
        playbackPosition = 0
    }

    fun getVideos(): List<Video> =
        videoRepository.videosMap[playlistId] ?: listOf()
}