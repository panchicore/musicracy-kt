package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.PlaylistModel
import javax.inject.Inject

class GetCurrentPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(): Result<PlaylistModel?>{
        return repository.getCurrentPlaylist()
    }
}