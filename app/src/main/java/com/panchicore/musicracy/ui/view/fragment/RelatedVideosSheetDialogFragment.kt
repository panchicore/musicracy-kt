package com.panchicore.musicracy.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.databinding.FragmentRelatedVideosSheetDialogBinding
import com.panchicore.musicracy.ui.view.adapter.RelatedVideoAdapter
import com.panchicore.musicracy.ui.viewmodel.PlaylistItemsViewModel
import com.panchicore.musicracy.ui.viewmodel.RelatedVideosViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [RelatedVideosSheetDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class RelatedVideosSheetDialogFragment : BottomSheetDialogFragment() {

    private val playlistItemsViewModel: PlaylistItemsViewModel by viewModels()
    private val relatedVideosViewModel: RelatedVideosViewModel by viewModels()
    private var _binding: FragmentRelatedVideosSheetDialogBinding? = null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRelatedVideosSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        relatedVideosViewModel.getRelatedVideos(param1!!)

        relatedVideosViewModel.relatedVideos.observe(this, {relatedVideos ->
            val adapter = RelatedVideoAdapter(relatedVideos, playlistItemsViewModel)
            binding.rvRelatedVideosItems2.adapter = adapter
        })

        relatedVideosViewModel.loadingRelatedVideos.observe(this, {isLoading ->
            binding.progressBar.isVisible = isLoading
        })
    }

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
        @JvmStatic
        fun newInstance(param1: String) =
            RelatedVideosSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}