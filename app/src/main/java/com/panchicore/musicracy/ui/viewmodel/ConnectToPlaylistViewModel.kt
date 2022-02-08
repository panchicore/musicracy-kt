package com.panchicore.musicracy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.domain.ConnectToPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectToPlaylistViewModel @Inject constructor(
    private val connectToPlaylistUseCase: ConnectToPlaylistUseCase
): ViewModel() {
    val connectToPlaylistModel = MutableLiveData<PlaylistModel>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun onConnect(name: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = connectToPlaylistUseCase(name)
            if(result.isSuccess){
                connectToPlaylistModel.postValue(result.getOrNull())
            }else{
                error.postValue(result.exceptionOrNull().toString())
            }
            isLoading.postValue(false)
        }
    }
}