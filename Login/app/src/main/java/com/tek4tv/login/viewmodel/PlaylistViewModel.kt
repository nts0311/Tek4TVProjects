package com.tek4tv.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tek4tv.login.model.PlaylistItem
import com.tek4tv.login.network.PlaylistBody
import com.tek4tv.login.network.Resource
import com.tek4tv.login.repositories.UserRepository
import com.tek4tv.login.repositories.VideoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaylistViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository
) : ViewModel() {
    private val _playlist = MutableLiveData<List<PlaylistItem>>()
    val playList: LiveData<List<PlaylistItem>> = _playlist

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var getPlayListDetailJob : Job? = null

    fun getPlaylist() {
        getPlayListDetailJob?.cancel()
         getPlayListDetailJob = viewModelScope.launch {
            when (val resource = videoRepository.getPlaylists(userRepository.currentToken)) {
                is Resource.Error -> {
                    _error.value = resource.message
                }

                is Resource.Success -> {
                    _playlist.value = resource.data
                }
            }
        }
    }
}