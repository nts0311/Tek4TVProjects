package com.tek4tv.login.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.tek4tv.login.R
import com.tek4tv.login.model.PlaylistItem
import com.tek4tv.login.ui.adapter.TabStateAdapter
import com.tek4tv.login.viewmodel.PlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_playlist.*


@AndroidEntryPoint
class PlaylistActivity : AppCompatActivity() {

    private val viewModel: PlaylistViewModel by viewModels()
    private lateinit var stateAdapter: TabStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        title = "Videos"

        stateAdapter = TabStateAdapter(this)

        setupObservers()
        viewModel.getPlaylist()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && vp_main.currentItem > 0) {
            vp_main.currentItem = 0
            true
        } else
            super.onKeyDown(keyCode, event)
    }

    private fun setupObservers() {
        viewModel.playList.observe(this)
        {
            setupViewPager(it)
        }
        viewModel.error.observe(this)
        {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.e("err: Playlist Activity", it)
        }
    }

    private fun setupViewPager(playlists: List<PlaylistItem>) {
        stateAdapter.playlists = playlists
        vp_main.adapter = stateAdapter

        TabLayoutMediator(tab_layout, vp_main) { tab, position ->
            tab.text = playlists[position].name
        }.attach()

        vp_main.offscreenPageLimit = 2
    }
}