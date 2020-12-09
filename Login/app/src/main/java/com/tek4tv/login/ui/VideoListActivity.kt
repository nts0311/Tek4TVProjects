package com.tek4tv.login.ui

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

        setupRecycleView()
        registerObservers()
        viewModel.getVideos()
    }

    private fun setupRecycleView()
    {
        rv_videos.adapter = videosAdapter
        rv_videos.layoutManager = LinearLayoutManager(this)
    }

    private fun registerObservers()
    {
        viewModel.videos.observe(this)
        {
            videosAdapter.videos = it
        }
    }
}