package com.tek4tv.login.viewmodel

import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tek4tv.login.repositories.UserRepository
import com.tek4tv.login.repositories.VideoRepository
import com.tek4tv.login.model.Video
import com.tek4tv.login.network.PlaylistBody
import com.tek4tv.login.network.PlaylistDetailResponse
import com.tek4tv.login.network.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VideoListViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {
    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var getVideosJob: Job? = null

    fun getVideos(playlistId: String) {
        getVideosJob?.cancel()
        getVideosJob = viewModelScope.launch {
            val response = videoRepository.getPlaylistDetail(
                userRepository.currentToken,
                playlistId
            )

            when (response) {
                is Resource.Error -> {
                    _error.value = response.message
                }
                is Resource.Success -> {
                    _videos.value = response.data.videos
                    videoRepository.videosMap[playlistId] = response.data.videos
                }
            }
        }
    }
}