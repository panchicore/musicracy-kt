package com.panchicore.musicracy.ui.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.databinding.ItemPlaylistItemRowBinding
import com.panchicore.musicracy.ui.view.fragment.RelatedVideosSheetDialogFragment

class PlaylistItemAdapter(private val playlistItems: ArrayList<PlaylistItemModel>, private val supportFragmentManager: FragmentManager) :
    RecyclerView.Adapter<PlaylistItemAdapter.PlaylistItemHolder>() {

    fun addPlaylistItem(item: PlaylistItemModel){
        playlistItems.add(item)
        notifyItemInserted(playlistItems.size - 1)
    }

    fun removePlaylistItem(item: PlaylistItemModel){
        val index = playlistItems.indexOfFirst{
            it.objectId == item.objectId
        }
        playlistItems.remove(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistItemRowBinding.inflate(layoutInflater)
        return PlaylistItemHolder(binding, supportFragmentManager)
    }

    override fun getItemCount(): Int = playlistItems.size

    override fun onBindViewHolder(holder: PlaylistItemHolder, position: Int) {
        holder.bind(playlistItems[position])
    }

    class PlaylistItemHolder(private val binding: ItemPlaylistItemRowBinding, private val supportFragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var playlistItem: PlaylistItemModel? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(playlistItem: PlaylistItemModel) {
            this.playlistItem = playlistItem
            binding.tvName.text = playlistItem.name
            binding.tvUser.text = playlistItem.user?.username
            Glide.with(binding.root.context).load(playlistItem.thumbnailUrl).into(binding.ivThumbnail)
        }

        override fun onClick(v: View?) {
            RelatedVideosSheetDialogFragment.newInstance(playlistItem?.sourceId!!).apply {
                show(supportFragmentManager, RelatedVideosSheetDialogFragment.TAG)
            }
        }
    }
}