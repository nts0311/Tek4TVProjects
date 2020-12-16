package com.tek4tv.login.network

import com.squareup.moshi.Json
import com.tek4tv.login.model.Video

class PlaylistDetailResponse(
    @Json(name = "Media")
    val videos: List<Video>
)