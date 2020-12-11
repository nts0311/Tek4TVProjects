package com.tek4tv.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tek4tv.login.R
import com.tek4tv.login.viewmodel.VideoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_video_list.*

@AndroidEntryPoint
class VideoListActivity : AppCompatActivity() {
    private val viewModel : VideoListViewModel by viewModels()
    private val videosAdapter = VideoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        title = "Videos"

        setupRecycleView()
        registerObservers()
        viewModel.getVideos()
    }

    private fun setupRecycleView()
    {
        rv_videos.adapter = videosAdapter
        rv_videos.layoutManager = LinearLayoutManager(this)
        videosAdapter.videoClickListener= {
            val intent = Intent(applicationContext, VideoPlayerActivity::class.java)
            intent.putExtra(VideoPlayerActivity.VIDEO_KEY, it)
            startActivity(intent)
        }
    }

    private fun registerObservers()
    {
        viewModel.videos.observe(this)
        {
            videosAdapter.videos = it
        }
    }
}