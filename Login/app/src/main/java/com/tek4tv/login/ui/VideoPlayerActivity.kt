package com.tek4tv.login.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.ext.cast.SessionAvailabilityListener
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.tek4tv.login.R
import com.tek4tv.login.model.Video
import com.tek4tv.login.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.exo_player_control_view.*
import kotlinx.coroutines.delay

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private lateinit var videoView: PlayerView


    private val videosAdapter = VideoAdapter()

    private val viewModel: VideoPlayerViewModel by viewModels()

    private lateinit var audioManager: AudioManager

    private lateinit var castPlayer: CastPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        setupRecycleView()

        if (viewModel.curVideo == null)
            viewModel.curVideo = intent.getSerializableExtra(VIDEO_KEY) as Video

        viewModel.playlistId = intent.getStringExtra(PLAYLIST_ID_KEY) ?: ""

        txt_vid_name.text = viewModel.curVideo!!.title


        videoView = findViewById(R.id.video_view)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi()
            videoView.layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            btnRotate.setImageResource(R.drawable.ic_baseline_fullscreen_exit_24)

            txt_video_title_player.visibility = View.VISIBLE
        } else {
            showSystemUi()
            videoView.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            btnRotate.setImageResource(R.drawable.ic_baseline_fullscreen_24)

            txt_video_title_player.visibility = View.GONE
        }

        btnRotate.setOnClickListener {
            val orientation = resources.configuration.orientation
            requestedOrientation = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }

        CastButtonFactory.setUpMediaRouteButton(this, cast_btn)
        castPlayer = CastPlayer(CastContext.getSharedInstance(this))
        castPlayer.setSessionAvailabilityListener(object : SessionAvailabilityListener {
            override fun onCastSessionAvailable() {

                val mediaItem = MediaItem.fromUri(viewModel.curVideo!!.path)

                castPlayer.setMediaItem(mediaItem, viewModel.playbackPosition)
                castPlayer.playWhenReady = true
                castPlayer.prepare()
            }

            override fun onCastSessionUnavailable() {

            }
        })

        lifecycleScope.launchWhenCreated {
            delay(1500)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    private fun hideSystemUi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            // hide status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

    }

    private fun showSystemUi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            // Show status bar
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        videoView.player = player
        player?.playWhenReady = true

        player?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    if (sw_autoplay.isChecked) {
                        val nextVideo = getNextVideo()
                        playVideo(nextVideo)
                    }
                }
            }
        })

        initAudio()

        playVideo(viewModel.curVideo)
    }

    private fun releasePlayer() {
        if (player != null) {
            viewModel.apply {
                playbackPosition = player?.currentPosition!!
                currentWindow = player?.currentWindowIndex!!
            }
            player?.release()
            player = null
        }
    }

    private fun setupRecycleView() {
        videosAdapter.videos = viewModel.getVideos()
        videosAdapter.videoClickListener = {
            player?.clearMediaItems()
            playVideo(it)
        }
        rv_video_list.adapter = videosAdapter
        rv_video_list.layoutManager = LinearLayoutManager(this)
        rv_video_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val canScrollUp = rv_video_list.canScrollVertically(-1)
                if (!canScrollUp) {
                    if (txt_vid_name.visibility != View.VISIBLE)
                        txt_vid_name.visibility = View.VISIBLE
                } else {
                    if (txt_vid_name.visibility != View.GONE)
                        txt_vid_name.visibility = View.GONE
                }
            }
        })
    }

    private fun playVideo(video: Video?) {
        if (video == null) return

        if (video.id != viewModel.curVideo?.id)
            viewModel.resetVideoParams()

        val mediaItem = MediaItem.fromUri(video.path)
        txt_vid_name.text = video.title
        txt_video_title_player.text = video.title

        viewModel.curVideo = video
        videosAdapter.videos = viewModel.getVideos().filter { it.id != video.id }

        player?.apply {
            setMediaItem(mediaItem)
            seekTo(viewModel.currentWindow, viewModel.playbackPosition)
            prepare()
        }
    }

    private fun getNextVideo(): Video {
        val videos = viewModel.getVideos()

        val i = videos.indexOf(viewModel.curVideo)

        return if (i == videos.size - 1) videos.first()
        else videos[i + 1]
    }

    private fun initAudio() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val type = player!!.audioStreamType
        val maxVolume = audioManager.getStreamMaxVolume(type)
        val i = 100 / maxVolume

        val currentVolume = audioManager.getStreamVolume(type)
        volume_bar.progress = i * currentVolume


        audioManager.isVolumeFixed
        volume_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                audioManager.setStreamVolume(
                    type,
                    progress / i,
                    0
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    companion object {
        const val VIDEO_KEY = "videos_key"
        const val PLAYLIST_ID_KEY = "playlist_id_key"
    }
}