package com.tek4tv.login.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Video(
    @Json(name = "ID")
    val id: Int,
    @Json(name = "ThumbNail")
    val thumbUrl: String,
    @Json(name = "CreateDate")
    val createDate: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Description")
    val description: String,
    @Json(name = "IsSchedule")
    val isSchedule: Boolean,
    @Json(name = "Schedule")
    val schedule: String,
    @Json(name = "Status")
    val status: String,
    @Json(name = "Media")
    val media: Media,
    @Json(name = "Playlist")
    val playList: PlayList,
    val path: String = "https://storage.tek4tv.vn/MAM/DAI_PHAT_THANH_TRUYEN_HINH_TRA_VINH/2020/11/06/CANG_LONG_PHAT_HUY_HIEU_QUA_SUC_MANH_DAN_VAN_KHEO/182854_Cang_Long_phat_huy_hieu_qua_suc_manh_Dan_van_kheo.mp4"
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
