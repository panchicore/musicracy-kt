package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.PlaylistModel
import javax.inject.Inject

class ConnectToPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(name: String): Result<PlaylistModel> {
        return repository.connectToPlaylist(name)
    }
}