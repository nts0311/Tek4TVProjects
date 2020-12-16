package com.tek4tv.login.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tek4tv.login.model.PlaylistItem
import com.tek4tv.login.ui.VideosFragment

class TabStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var playlists = listOf<PlaylistItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = playlists.size

    override fun createFragment(position: Int): Fragment =
        VideosFragment.newInstance(playlists[position].id)
}