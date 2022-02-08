package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.youtube.VideoItem
import javax.inject.Inject

class SubmitVideoItemUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(item: VideoItem): Result<PlaylistItemModel> {
        return repository.submitVideoItem(item)
    }
}