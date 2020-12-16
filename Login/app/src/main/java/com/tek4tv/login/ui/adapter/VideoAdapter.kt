package com.tek4tv.login.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tek4tv.login.R
import com.tek4tv.login.model.Video


class VideoAdapter : RecyclerView.Adapter<VideoViewHolder>() {
    var videos: List<Video> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var videoClickListener: (Video) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position], videoClickListener)
    }

    override fun getItemCount(): Int = videos.size
}

class VideoViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    private val imgThumb = root.findViewById<ImageView>(R.id.img_video_thumb)
    private val txtTitle = root.findViewById<TextView>(R.id.txt_video_title)
    private val txtCreatedDate = root.findViewById<TextView>(R.id.txt_created_date)

    fun bind(video: Video, itemClickListener: (Video) -> Unit) {
        Glide.with(imgThumb.context)
            .load(video.thumbUrl)
            .into(imgThumb)

        root.setOnClickListener {
            itemClickListener(video)
        }

        txtTitle.text = video.title
        txtCreatedDate.text = video.schedule
    }

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val root = inflater.inflate(R.layout.video_item, parent, false)
            return VideoViewHolder(root)
        }
    }
}