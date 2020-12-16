package com.tek4tv.login.network

import com.squareup.moshi.Json
import com.tek4tv.login.model.Video

class VideosResponse(
    @Json(name = "Total")
    val total: Int,
    @Json(name = "Result")
    val result: List<Video>
)
