package com.tek4tv.login.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Video(
    @Json(name = "ID")
    val id: Int,
    @Json(name = "Description")
    val description: String,
    @Json(name = "Name")
    val title: String,
    @Json(name = "ThumbNail")
    val thumbUrl: String,
    @Json(name = "Path")
    val path: String,
    @Json(name = "Schedule")
    val schedule: String,
) : Serializable

data class Media(
    @Json(name = "ID")
    val id: Int,
    @Json(name = "Name")
    val name: String
) : Serializable

data class PlayList(
    @Json(name = "Name")
    val name: String,
    @Json(name = "ID")
    val id: Int,
    @Json(name = "Icon")
    val iconUrl: String,
    @Json(name = "Color")
    val color: String
) : Serializable
