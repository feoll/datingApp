package com.example.datingapp.ui.adapters.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapp.databinding.ProfileCardItemBinding
import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.BlurHashDecoder

class CardStackAdapter() : RecyclerView.Adapter<CardStackAdapter.CardViewHolder>() {

    private var listCard: List<ProfileCard> = emptyList()

    inner class CardViewHolder(private val binding: ProfileCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ProfileCard) {
            binding.apply {
                cardCity.visibility = View.GONE
                cardDescription.visibility = View.GONE
                cardAge.visibility = View.GONE
                space.visibility = View.GONE

                cardName.text = profile.name
                profile.age?.let {
                    cardAge.text = it.toString()
                    cardAge.visibility = View.VISIBLE
                    space.visibility = View.VISIBLE
                }
                profile.about?.let {
                    cardDescription.text = it
                    cardDescription.visibility = View.VISIBLE
                }
                profile.city?.let {
                    cardCity.text = it
                    cardCity.visibility = View.VISIBLE
                }

                setExistsImage(profile, cardImage, imageCountLayout, imageCount, profile.selectedImage)

                //Switch image
                leftSide.setOnClickListener {
                    if(profile.selectedImage > 0) {
                        profile.selectedImage--
                        setExistsImage(profile, cardImage, imageCountLayout, imageCount, profile.selectedImage)
                        saveLastVisibleProfile?.invoke(profile)
                    }
                }
                rightSide.setOnClickListener {
                    if(profile.selectedImage < profile.imageUrls.size - 1) {
                        profile.selectedImage++
                        setExistsImage(profile, cardImage, imageCountLayout, imageCount, profile.selectedImage)
                        saveLastVisibleProfile?.invoke(profile)
                    }
                }
                //Setup button click
                likeButton.setOnClickListener { onLikeClick?.invoke(profile) }
                dislikeButton.setOnClickListener { onDislikeClick?.invoke(profile) }
                bottomSide.setOnClickListener { bottomSideClick?.invoke(profile) }
            }
        }

        private fun setCountImage(profile: ProfileCard, imageCountLayout: LinearLayout, countTextView: TextView, imageId: Int) {
            if(profile.imageUrls.size <= 1) {
                imageCountLayout.isVisible = false
            } else {
                imageCountLayout.isVisible = true
                countTextView.text = "${imageId + 1}/${profile.imageUrls.size}"
            }
        }

        private fun setExistsImage(profile: ProfileCard, imageView: ImageView, imageCountLayout: LinearLayout, countTextView: TextView, imageId: Int) {
            setCountImage(profile, imageCountLayout, countTextView, imageId)
            if (profile.imageUrls.isNotEmpty()
                && profile.blurs.isNotEmpty()
                && profile.imageUrls.size > imageId
                && profile.blurs.size > imageId
            ) {
                setImage(profile, imageView, imageId)
                return
            }
            imageView.setImageResource(android.R.color.holo_purple)
        }

        private fun setImage(profile: ProfileCard, imageView: ImageView, imageId: Int) {
            val blurHash = BlurHashDecoder.blurHashBitmap(itemView.resources, profile.blurs[imageId])

            Glide.with(itemView)
                .load(profile.imageUrls[imageId].replace("localhost", "192.168.0.197"))
                .placeholder(blurHash)
                .into(imageView)
        }
    }

    fun setList(list: List<ProfileCard>) {
        listCard = list
        notifyDataSetChanged()
    }

    fun getCurrentList() = listCard


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            ProfileCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if(listCard.isNotEmpty()) {
            val card = listCard[position]
            holder.bind(card)
        }
    }

    override fun getItemCount() = listCard.size

    private var onLikeClick: ((ProfileCard) -> Unit)? = null
    private var onDislikeClick: ((ProfileCard) -> Unit)? = null
    private var bottomSideClick: ((ProfileCard) -> Unit)? = null
    private var saveLastVisibleProfile: ((ProfileCard) -> Unit)? = null

    fun setOnLikeClickListener(block: (ProfileCard) -> Unit) {
        onLikeClick = block
    }

    fun setOnDislikeClickListener(block: (ProfileCard) -> Unit) {
        onDislikeClick = block
    }

    fun setOnBottomSideClickListener(block: (ProfileCard) -> Unit) {
        bottomSideClick = block
    }

    fun saveLastVisibleProfile(block: (ProfileCard) -> Unit) {
        saveLastVisibleProfile = block
    }
}