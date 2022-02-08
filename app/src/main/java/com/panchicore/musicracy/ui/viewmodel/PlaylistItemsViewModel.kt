package com.panchicore.musicracy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.domain.GetCurrentPlaylistUseCase
import com.panchicore.musicracy.domain.GetPlaylistItemsUseCase
import com.panchicore.musicracy.domain.GetYoutubeVideoInfoByUrlUseCase
import com.panchicore.musicracy.domain.SubmitVideoItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistItemsViewModel @Inject constructor(
    private val getPlaylistItemsUseCase: GetPlaylistItemsUseCase,
    private val submitVideoItemUseCase: SubmitVideoItemUseCase,
    private val getCurrentPlaylistUseCase: GetCurrentPlaylistUseCase,
    private val getYoutubeVideoInfoByUrlUseCase: GetYoutubeVideoInfoByUrlUseCase
) : ViewModel() {
    val newItem = MutableLiveData<PlaylistItemModel>()
    val currentPlaylist = MutableLiveData<PlaylistModel>()
    val playlistItems = MutableLiveData<ArrayList<PlaylistItemModel>>()
    val loadingPlaylistItems = MutableLiveData<Boolean>()
    val youtubeVideoInfo = MutableLiveData<VideoItem>()

    fun getCurrentPlaylist() {
        viewModelScope.launch {
            val result = getCurrentPlaylistUseCase()
            if (result.isSuccess) {
                currentPlaylist.postValue(result.getOrNull())
            }
        }
    }

    fun getPlaylistItems(playlist: PlaylistModel) {
        viewModelScope.launch {
            loadingPlaylistItems.postValue(true)
            val result = getPlaylistItemsUseCase(playlist)
            if (result.isSuccess) {
                playlistItems.postValue(result.getOrNull())
            }
            loadingPlaylistItems.postValue(false)
        }
    }

    fun submitVideoItem(item: VideoItem) {
        viewModelScope.launch {
            val result = submitVideoItemUseCase(item)
            if (result.isSuccess) {
                newItem.postValue(result.getOrNull())
            }
        }
    }

    fun getYoutubeVideoInfo(videoUrl: String) {
        viewModelScope.launch {
            val result = getYoutubeVideoInfoByUrlUseCase(videoUrl)
            if (result.isSuccess) {
                youtubeVideoInfo.postValue(result.getOrNull())
            }
        }
    }


}