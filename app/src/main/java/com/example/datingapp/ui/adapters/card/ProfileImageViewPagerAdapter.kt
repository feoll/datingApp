package com.example.datingapp.ui.adapters.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.datingapp.databinding.ProfileImageViewpagerItemBinding
import com.example.datingapp.ui.models.Image
import com.example.datingapp.utils.BlurHashDecoder

class ProfileImageViewPagerAdapter() :
    RecyclerView.Adapter<ProfileImageViewPagerAdapter.ImageViewHolder>() {

    private var listCard: List<Image> = emptyList()

    inner class ImageViewHolder(private val binding: ProfileImageViewpagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.apply {
                val blurHash = BlurHashDecoder.blurHashBitmap(itemView.resources, image.blur)

                Glide.with(itemView)
                    .load(image.imageUrl.replace("localhost", "192.168.0.197"))
                    .placeholder(blurHash)
                    .into(profileImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ProfileImageViewpagerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val card = listCard[position]
        holder.bind(card)

    }


    fun setList(list: List<Image>) {
        listCard = list
        notifyDataSetChanged()
    }


    override fun getItemCount() = listCard.size

}