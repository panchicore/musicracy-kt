package com.panchicore.musicracy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.domain.GetYoutubeRelatedVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelatedVideosViewModel @Inject constructor(
    private val getYoutubeRelatedVideosUseCase: GetYoutubeRelatedVideosUseCase
): ViewModel() {
    val relatedVideos = MutableLiveData<List<VideoItem>>()
    val loadingRelatedVideos = MutableLiveData<Boolean>()

    fun getRelatedVideos(videoId: String){
        viewModelScope.launch {
            loadingRelatedVideos.postValue(true)
            val result = getYoutubeRelatedVideosUseCase(videoId)
            if(result.isSuccess){
                relatedVideos.postValue(result.getOrNull())
            }
            loadingRelatedVideos.postValue(false)
        }
    }
}