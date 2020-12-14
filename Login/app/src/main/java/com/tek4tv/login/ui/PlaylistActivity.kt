package com.tek4tv.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.tek4tv.login.R
import com.tek4tv.login.model.PlaylistItem
import com.tek4tv.login.viewmodel.PlaylistViewModel
import com.tek4tv.login.viewmodel.VideoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_playlist.*


@AndroidEntryPoint
class PlaylistActivity : AppCompatActivity() {

    private val viewModel : PlaylistViewModel by viewModels()
    private lateinit var stateAdapter : TabStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        title = "Videos"

        stateAdapter = TabStateAdapter(this)

        setupObservers()
        viewModel.getPlaylist()
    }

    private fun setupObservers()
    {
        viewModel.playList.observe(this)
        {
            setupViewPager(it)
        }
        viewModel.error.observe(this)
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.e("err: Playlist Activity",it)
        }
    }

    private fun setupViewPager(playlists : List<PlaylistItem>)
    {
        stateAdapter.playlists = playlists
        vp_main.adapter = stateAdapter

        TabLayoutMediator(tab_layout, vp_main) {tab, position ->
                tab.text =  playlists[position].name
        }.attach()


    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

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
    }*/
}