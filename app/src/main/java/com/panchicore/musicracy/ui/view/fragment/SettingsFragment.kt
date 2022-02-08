package com.panchicore.musicracy.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panchicore.musicracy.R
import com.panchicore.musicracy.databinding.FragmentHistoryBinding
import com.panchicore.musicracy.databinding.FragmentSettingsBinding
import com.panchicore.musicracy.ui.view.ConnectToPlaylistActivity
import com.parse.ParseObject

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exitButton.setOnClickListener {
            ParseObject.unpinAll("currentPlaylist")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}