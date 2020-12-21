package com.tek4tv.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tek4tv.login.R
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



        viewModel = ViewModelProvider(requireActivity()).get(playlistId!!, VideoListViewModel::class.java)

        setupRecycleView()
        registerObservers()
        viewModel.getVideos(playlistId!!)
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    private fun setupRecycleView() {
        rv_videos.adapter = videosAdapter
        rv_videos.layoutManager = LinearLayoutManager(context)
        videosAdapter.videoClickListener = {
            /*val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VideoPlayerActivity.VIDEO_KEY, it)
            intent.putExtra(VideoPlayerActivity.PLAYLIST_ID_KEY, playlistId)
            startActivity(intent)*/

            (requireActivity() as PlaylistActivity).supportActionBar!!.hide()

            val frag = VideoPlayerFragment.newInstance(it, playlistId!!)

            parentFragmentManager.beginTransaction()
                .replace(R.id.play_screen_layout,frag,VideoPlayerFragment.TAG)
                .commitAllowingStateLoss()
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
            Log.e("err: VideoFrag", it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.video_list_menu, menu)

        val searchItem = menu.findItem(R.id.video_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val filteredList = viewModel.videos.value!!.filter {
                        it.title.toLowerCase().contains(query.toLowerCase())
                    }

                    videosAdapter.videos = filteredList
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText == "") {
                    videosAdapter.videos = viewModel.videos.value!!
                }
                return false
            }
        })
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