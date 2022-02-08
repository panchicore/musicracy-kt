package com.panchicore.musicracy.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.provider.UsernameProvider
import com.panchicore.musicracy.databinding.ActivityMainBinding
import com.panchicore.musicracy.ui.viewmodel.MainViewModel
import com.panchicore.musicracy.ui.viewmodel.PlaylistItemsViewModel
import com.parse.ParseAnonymousUtils
import com.parse.ParseQuery
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val playlistItemsViewModel: PlaylistItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val intent = Intent(this, ShareReceiverActivity::class.java)
        startActivity(intent)
        finish()
         */

        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            Log.d("MainActivity", "Anonymous user [${currentUser.objectId}] was logged in.")
            startActivityForLoggedInUser()
        } else {
            ParseAnonymousUtils.logIn { currentUser, e ->
                if (e != null) {
                    Log.d("MainActivity", e.toString())
                } else {
                    if(currentUser.isNew){
                        currentUser.username = UsernameProvider.pickAnUsername()
                        currentUser.saveInBackground()
                    }
                    Log.d("MainActivity", "Anonymous user [${currentUser.objectId}] logged in.")
                    startActivityForLoggedInUser()
                }
            }
        }
    }

    private fun startActivityForLoggedInUser() {
        /*
        User is logged in, when connected to a playlist redirect to it.
        When not, allow to search playlists to connect.
         */
        playlistItemsViewModel.getCurrentPlaylist()
        playlistItemsViewModel.currentPlaylist.observe(this, { currentPlaylist ->
            var intent = Intent(this, ConnectToPlaylistActivity::class.java)
            if (currentPlaylist != null) {
                intent = Intent(this, PlaylistTabsActivity::class.java)
            }
            startActivity(intent)
            finish()
        })
    }
}