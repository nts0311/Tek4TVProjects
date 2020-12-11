package com.tek4tv.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tek4tv.login.UserRepository
import com.tek4tv.login.VideoRepository
import com.tek4tv.login.model.Video
import kotlinx.coroutines.launch

class VideoListViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository
) :ViewModel() {
    private val _videos = MutableLiveData<List<Video>>()
    val videos : LiveData<List<Video>> = _videos

    fun getVideos()
    {
        viewModelScope.launch {
            val response = videoRepository.getVideos(2014, userRepository.currentToken)

            if(response != null && response.isSuccessful)
                _videos.value = response.body()!!.result
        }
    }
}