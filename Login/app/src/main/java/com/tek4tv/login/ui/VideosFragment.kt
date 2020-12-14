package com.tek4tv.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tek4tv.login.R
import com.tek4tv.login.network.Resource
import com.tek4tv.login.viewmodel.VideoListViewModel
import kotlinx.android.synthetic.main.fragment_videos.*


private const val ARG_PLAYLIST_ID = "param1"


class VideosFragment : Fragment() {
    private var playlistId: String? = null
    private val videosAdapter = VideoAdapter()

    lateinit var viewModel: VideoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playlistId = it.getString(ARG_PLAYLIST_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity!!).get(playlistId!!, VideoListViewModel::class.java)

        setupRecycleView()
        registerObservers()
        viewModel.getVideos(playlistId!!)
    }

    private fun setupRecycleView() {
        rv_videos.adapter = videosAdapter
        rv_videos.layoutManager = LinearLayoutManager(context)
        videosAdapter.videoClickListener = {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VideoPlayerActivity.VIDEO_KEY, it)
            startActivity(intent)
        }
    }

    private fun registerObservers() {
        viewModel.videos.observe(viewLifecycleOwner)
        {
            videosAdapter.videos = it
        }
        viewModel.error.observe(viewLifecycleOwner)
        {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.e("err: VideoFrag",it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(playlistId: String) =
            VideosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PLAYLIST_ID, playlistId)
                }
            }
    }
}