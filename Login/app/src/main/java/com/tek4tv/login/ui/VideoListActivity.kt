package com.tek4tv.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.tek4tv.login.R
import com.tek4tv.login.viewmodel.VideoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_video_list.*

@AndroidEntryPoint
class VideoListActivity : AppCompatActivity() {
    private val viewModel: VideoListViewModel by viewModels()
    private val videosAdapter = VideoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        title = "Videos"

        setupRecycleView()
        registerObservers()
        viewModel.getVideos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.video_list_menu, menu)

        val searchItem = menu?.findItem(R.id.video_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    viewModel.getVideos(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText == "")
                    viewModel.restoreAllVideoList()
                return false
            }
        })

        return true
    }

    private fun setupRecycleView() {
        rv_videos.adapter = videosAdapter
        rv_videos.layoutManager = LinearLayoutManager(this)
        videosAdapter.videoClickListener = {
            val intent = Intent(applicationContext, VideoPlayerActivity::class.java)
            intent.putExtra(VideoPlayerActivity.VIDEO_KEY, it)
            startActivity(intent)
        }
    }

    private fun registerObservers() {
        viewModel.videos.observe(this)
        {
            videosAdapter.videos = it
        }
    }
}