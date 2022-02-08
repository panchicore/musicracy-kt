package com.panchicore.musicracy.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.databinding.ActivityShareReceiverBinding
import com.panchicore.musicracy.ui.viewmodel.PlaylistItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareReceiverBinding
    private lateinit var video: VideoItem
    private lateinit var playlist: PlaylistModel
    private val playlistItemsViewModel: PlaylistItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent?.action == Intent.ACTION_SEND){
            if ("text/plain" == intent.type) {
                handleReceivedSharedLink(intent) // Handle text being sent
            }
        }

        playlistItemsViewModel.getCurrentPlaylist()


        playlistItemsViewModel.currentPlaylist.observe(this, {
            playlist = it
            binding.tvCurrentPlaylistName.text = playlist.name
        })

        playlistItemsViewModel.youtubeVideoInfo.observe(this, {
            video = it
            binding.progressBar.isVisible = false
            binding.tvVideoName.text = video.title
            Glide.with(binding.root.context).load(video.thumbnailUrl).into(binding.ivVideoThumbnail)
        })

        binding.buttonSend.setOnClickListener {
            playlistItemsViewModel.submitVideoItem(video)
        }

        playlistItemsViewModel.newItem.observe(this, { playlistItem ->
            Log.i("ShareReceiver", "Submitted: ${playlistItem.objectId.toString()}")
            Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show()
            finish()
        })

        // Receive the link
        // Get the video info
        // getVideoInfoByURL("https://youtu.be/yTOTxFdIUqA")
        // current playlist?
        // no: show error
        // yes: send video, show message
        // close

    }

    private fun handleReceivedSharedLink(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { videoUrl ->
            Log.i("URL", videoUrl)
            getVideoInfoByURL(videoUrl)
        }
    }

    private fun getVideoInfoByURL(url: String){
        playlistItemsViewModel.getYoutubeVideoInfo(url)
    }
}