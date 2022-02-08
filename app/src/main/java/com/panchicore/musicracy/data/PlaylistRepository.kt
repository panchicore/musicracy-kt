package com.panchicore.musicracy.data

import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.data.network.ParseService
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val parseService: ParseService,
) {

    suspend fun connectToPlaylist(name: String): Result<PlaylistModel> {
        return parseService.connectToPlaylist(name)
    }

    suspend fun getCurrentPlaylist(): Result<PlaylistModel?> {
        return parseService.getCurrentPlaylist()
    }

    suspend fun getPlaylistItems(playlist: PlaylistModel): Result<ArrayList<PlaylistItemModel>> {
        return parseService.getPlaylistItems(playlist)
    }

    suspend fun submitVideoItem(item: VideoItem): Result<PlaylistItemModel> {
        val playlist = getCurrentPlaylist().getOrNull()
        return parseService.submitVideoItem(playlist!!, item)
    }

    suspend fun getYoutubeVideoInfoByUrl(videoUrl: String): Result<VideoItem> {
        return parseService.getYoutubeVideoInfoByUrl(videoUrl)
    }

    suspend fun getYoutubeRelatedVideosByVideoId(videoId: String): Result<List<VideoItem>> {
        return parseService.getYoutubeRelatedVideosByVideoId(videoId)
    }
}