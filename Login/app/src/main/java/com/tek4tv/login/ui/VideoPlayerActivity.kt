package com.tek4tv.login.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.tek4tv.login.R
import com.tek4tv.login.model.Video
import com.tek4tv.login.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_video_player.*

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private lateinit var videoView : PlayerView
     

    private val videosAdapter = VideoAdapter()

   private val viewModel : VideoPlayerViewModel by viewModels()
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        setupRecycleView()

        if(viewModel.curVideo == null)
            viewModel.curVideo = intent.getSerializableExtra(VIDEO_KEY) as Video
        txt_vid_name.text = viewModel.curVideo!!.title

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
        playVideo(viewModel.curVideo)
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

    private fun setupRecycleView()
    {
        videosAdapter.videos = viewModel.videoList
        videosAdapter.videoClickListener= {
            playVideo(it)
        }
        rv_video_list.adapter = videosAdapter
        rv_video_list.layoutManager = LinearLayoutManager(this)
        rv_video_list.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val canScrollUp = rv_video_list.canScrollVertically(-1)
                if(!canScrollUp)
                {
                    if(txt_vid_name.visibility != View.VISIBLE)
                        txt_vid_name.visibility = View.VISIBLE
                }
                else
                {
                    if(txt_vid_name.visibility != View.GONE)
                        txt_vid_name.visibility = View.GONE
                }
            }
        })
    }

    private fun playVideo(video: Video?) {
        if(video == null) return

        if(video.id != viewModel.curVideo?.id)
            viewModel.resetVideoParams()

        val mediaItem = MediaItem.fromUri(video.path)
        txt_vid_name.text = video.title
        player?.apply {
            setMediaItem(mediaItem)
            playWhenReady = viewModel.playWhenReady
            seekTo(viewModel.currentWindow, viewModel.playbackPosition)
            prepare()
        }
        viewModel.curVideo = video
        videosAdapter.videos = viewModel.videoList.filter { it.id != video.id }
    }

    companion object
    {
        const val VIDEO_KEY = "videos_key"
    }
}