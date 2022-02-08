package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.youtube.VideoItem
import javax.inject.Inject

class GetYoutubeRelatedVideosUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(videoId: String): Result<List<VideoItem>> {
        return repository.getYoutubeRelatedVideosByVideoId(videoId)
    }
}