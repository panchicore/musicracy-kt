package com.panchicore.musicracy.data.model.youtube

import com.google.gson.annotations.SerializedName

data class VideoItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("channelTitle") val channelTitle: String,
    @SerializedName("publishedAt") val publishedAt: String
)