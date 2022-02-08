package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.youtube.VideoItem
import javax.inject.Inject

class GetYoutubeVideoInfoByUrlUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(videoUrl: String): Result<VideoItem> {
        return repository.getYoutubeVideoInfoByUrl(videoUrl)
    }
}