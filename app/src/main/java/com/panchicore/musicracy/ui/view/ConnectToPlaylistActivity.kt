package com.panchicore.musicracy.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.panchicore.musicracy.databinding.ActivityConnectToPlaylistBinding
import com.panchicore.musicracy.ui.viewmodel.ConnectToPlaylistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectToPlaylistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectToPlaylistBinding
    private val connectToPlaylistViewModel: ConnectToPlaylistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectToPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConnect.setOnClickListener {
            val name = binding.editTextPlaylistName.text.toString()
            connectToPlaylistViewModel.onConnect(name)
        }

        connectToPlaylistViewModel.isLoading.observe(this, {isLoading ->
            binding.progressBar.isVisible = isLoading
        })

        connectToPlaylistViewModel.connectToPlaylistModel.observe(this, { playlist ->
            val intent = Intent(this, PlaylistTabsActivity::class.java)
            startActivity(intent)
            finish()
        })

        connectToPlaylistViewModel.error.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })
    }
}