package com.tek4tv.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tek4tv.login.VideoRepository
import com.tek4tv.login.model.Video

class VideoPlayerViewModel @ViewModelInject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition: Long = 0
    var curVideo : Video? = null

    var videoList = videoRepository.videoList

    fun resetVideoParams()
    {
        playWhenReady = true
        currentWindow = 0
        playbackPosition = 0
    }
}