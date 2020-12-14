package com.tek4tv.login.model

import com.squareup.moshi.Json

data class PlaylistItem(
        @Json(name = "ID")
        val id: String,
        @Json(name = "Name")
        val name: String,
        @Json(name = "Description")
        val description: String,
        @Json(name = "Color")
        val color: String,
        @Json(name = "Icon")
        val icon: String,
)