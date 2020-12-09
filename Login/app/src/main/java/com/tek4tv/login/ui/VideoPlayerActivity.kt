package com.tek4tv.login.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.tek4tv.login.R
import com.tek4tv.login.model.Video
import com.tek4tv.login.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private lateinit var videoView : PlayerView
    private var video : Video? = null

   private val viewModel : VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        video = intent.getSerializableExtra(VIDEO_KEY) as Video
        videoView = findViewById(R.id.video_view)
    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT >= 24)
        {
            initPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    override fun onResume() {
        super.onResume()
        if(Util.SDK_INT < 24)
        {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    private fun initPlayer()
    {
        player = SimpleExoPlayer.Builder(this).build()
        videoView.player = player
        val mediaItem = MediaItem.fromUri(video!!.path)
        player?.apply {
            setMediaItem(mediaItem)
            playWhenReady = playWhenReady
            seekTo(viewModel.currentWindow, viewModel.playbackPosition)
            prepare()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            viewModel.apply {
                playWhenReady = player?.playWhenReady!!
                playbackPosition = player?.currentPosition!!
                currentWindow = player?.currentWindowIndex!!
            }
            player?.release()
            player = null
        }
    }

    companion object
    {
        const val VIDEO_KEY = "videos_key"
    }
}