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


    /*@Json(name = "CreateDate")
    val createDate: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "IsSchedule")
    val isSchedule: Boolean,

    @Json(name = "Status")
    val status: String,
    @Json(name = "Media")
    val media: Media,
    @Json(name = "Playlist")
    val playList: PlayList,*/

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
