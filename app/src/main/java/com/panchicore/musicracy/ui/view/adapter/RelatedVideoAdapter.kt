package com.panchicore.musicracy.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.panchicore.musicracy.databinding.ItemRelatedVideoRowBinding
import com.panchicore.musicracy.ui.viewmodel.PlaylistItemsViewModel

class RelatedVideoAdapter(private val relatedVideoItems: List<VideoItem>, private val vm: PlaylistItemsViewModel) :
    RecyclerView.Adapter<RelatedVideoAdapter.RelatedVideoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedVideoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRelatedVideoRowBinding.inflate(layoutInflater)
        return RelatedVideoHolder(binding, vm)
    }

    override fun getItemCount(): Int = relatedVideoItems.size

    override fun onBindViewHolder(holder: RelatedVideoHolder, position: Int) {
        holder.bind(relatedVideoItems[position])
    }

    class RelatedVideoHolder(private val binding: ItemRelatedVideoRowBinding, private val vm: PlaylistItemsViewModel) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        init {
            binding.root.setOnLongClickListener(this)
        }

        var relatedVideo: VideoItem? = null

        fun bind(videoItem: VideoItem) {
            this.relatedVideo = videoItem
            binding.tvTitle.text = videoItem.title
            binding.tvChannelTitle.text = videoItem.channelTitle
            Glide.with(binding.root.context).load(videoItem.thumbnailUrl).into(binding.ivThumbnail)
        }

        override fun onLongClick(v: View?): Boolean {
            vm.submitVideoItem(relatedVideo!!)
            Toast.makeText(binding.root.context, "Done!", Toast.LENGTH_SHORT).show()
            return true
        }

    }
}