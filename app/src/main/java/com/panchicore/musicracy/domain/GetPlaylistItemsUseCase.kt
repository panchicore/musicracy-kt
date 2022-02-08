package com.panchicore.musicracy.domain

import com.panchicore.musicracy.data.PlaylistRepository
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import javax.inject.Inject

class GetPlaylistItemsUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: PlaylistModel): Result<ArrayList<PlaylistItemModel>>{
        return repository.getPlaylistItems(playlist)
    }
}