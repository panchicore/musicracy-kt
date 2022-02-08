package com.panchicore.musicracy.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.databinding.FragmentPlayingNowBinding
import com.panchicore.musicracy.ui.view.adapter.PlaylistItemAdapter
import com.panchicore.musicracy.ui.viewmodel.PlaylistItemsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayingNowFragment : Fragment() {

    private var _binding: FragmentPlayingNowBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaylistItemAdapter
    private lateinit var playlist: PlaylistModel
    private val playlistItemsViewModel: PlaylistItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayingNowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPlaylistItems.layoutManager = LinearLayoutManager(context)

        binding.swipeContainer.setOnRefreshListener {
            playlistItemsViewModel.getCurrentPlaylist()
        }

        playlistItemsViewModel.getCurrentPlaylist()
        playlistItemsViewModel.currentPlaylist.observe(this, {
            playlist = it
            playlistItemsViewModel.getPlaylistItems(playlist)
        })
        playlistItemsViewModel.playlistItems.observe(this, { playlistItems ->
            Log.i("Received:playlistItems", playlistItems.size.toString())
            adapter = PlaylistItemAdapter(playlistItems, parentFragmentManager)
            binding.rvPlaylistItems.adapter = adapter
        })
        playlistItemsViewModel.loadingPlaylistItems.observe(this, { isLoading ->
            binding.swipeContainer.isRefreshing = isLoading
        })
        playlistItemsViewModel.newItem.observe(this, { newItem ->
            Log.i("newItem >>>", newItem.toString())
            adapter.addPlaylistItem(newItem)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}