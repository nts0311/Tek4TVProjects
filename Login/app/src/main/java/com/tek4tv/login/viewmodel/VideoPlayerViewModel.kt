package com.tek4tv.login.viewmodel

import androidx.lifecycle.ViewModel

class VideoPlayerViewModel : ViewModel() {
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition: Long = 0
}