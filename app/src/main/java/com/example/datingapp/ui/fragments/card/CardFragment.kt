package com.example.datingapp.ui.fragments.card

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.datingapp.databinding.FragmentCardBinding
import com.example.datingapp.ui.adapters.card.CardStackAdapter
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.CardViewModel
import com.example.datingapp.utils.Resource
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding>() {

    private val viewModel by activityViewModels<CardViewModel>()
    private lateinit var cardStackLayoutManager: CardStackLayoutManager
    private val cardStackAdapter by lazy { CardStackAdapter() }

    override fun getViewBinding() = FragmentCardBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCardStack(view.context)
        subscribeCardEvents()
        setupListeners()
    }

    private fun setupListeners() {
        cardStackAdapter.saveLastVisibleProfile { profile ->
            viewModel.lastVisibleProfile = profile
        }
        cardStackAdapter.setOnLikeClickListener { profile ->
            likeClick()
        }
        cardStackAdapter.setOnDislikeClickListener { profile ->
            dislikeClick()
        }
        cardStackAdapter.setOnBottomSideClickListener { profile ->
            val topPosition = cardStackLayoutManager.cardStackState.topPosition + 1
            val profileNext = cardStackAdapter.getCurrentList().elementAtOrNull(topPosition)
            val direction = CardFragmentDirections.actionCardFragmentToCardDetailsFragment(profile, profileNext)
            findNavController().navigate(direction)
        }
    }

    private fun subscribeCardEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            //Get profiles data
            viewModel.profilesState.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        hideProgressBar()
                        var lastPosition = -1
                        viewModel.lastVisibleProfile?.let { profile ->
                            lastPosition = resource.data.indexOfFirst { it.profileId == profile.profileId }
                            if(lastPosition != -1) {
                                resource.data[lastPosition].selectedImage = profile.selectedImage
                            }
                        }
                        cardStackAdapter.setList(resource.data)
                        binding.cardStackView.scrollToPosition(lastPosition)
                        Log.d("message", "Success")
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                        hideProgressBar()
                        Log.d("message", resource.message)
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }

                    else -> Log.d("message", "Undefined")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noCategoryData.collectLatest {
                if(it) showNoCategoryDataMessage() else hideNoCategoryDataMessage()
            }
        }
    }


    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    override fun onStop() {
        super.onStop()
        findAndSaveLastVisibleProfile()
    }

    private fun findAndSaveLastVisibleProfile() {
        val topPosition = cardStackLayoutManager.cardStackState.topPosition
        val profile = cardStackAdapter.getCurrentList().elementAtOrNull(topPosition)
        viewModel.lastVisibleProfile = profile
        Log.d("message", topPosition.toString())
    }


    private fun setupCardStack(context: Context) {
        cardStackLayoutManager = CardStackLayoutManager(context, object: CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {}
            override fun onCardRewound() {}
            override fun onCardCanceled() {}
            override fun onCardAppeared(view: View?, position: Int) {
                Log.d("message", position.toString())
            }
            override fun onCardDisappeared(view: View?, position: Int) {}
            override fun onCardSwiped(direction: Direction?) {
                val topPosition = cardStackLayoutManager.cardStackState.topPosition - 1
                val profile = cardStackAdapter.getCurrentList().elementAtOrNull(topPosition)

                //Hide card
                if(topPosition + 1 >= cardStackAdapter.getCurrentList().size) {
                    viewModel.noCategoryData.value = true
                }
                when(direction) {
                    Direction.Left -> {
                        Log.d("message", "Direction Left ${profile?.name}")
                    }
                    Direction.Right -> {
                        Log.d("message", "Direction Right ${profile?.name}")
                    }
                    else -> Unit
                }
            }
        })
        cardStackLayoutManager.apply {
            setStackFrom(StackFrom.None)
            setVisibleCount(2)
            setTranslationInterval(12.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.15f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        binding.cardStackView.layoutManager = cardStackLayoutManager
        binding.cardStackView.adapter = cardStackAdapter
    }

    private fun likeClick() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        cardStackLayoutManager.setSwipeAnimationSetting(setting)
        binding.cardStackView.swipe()
    }

    private fun dislikeClick() {
        val setting = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        cardStackLayoutManager.setSwipeAnimationSetting(setting)
        binding.cardStackView.swipe()
    }

    private fun showNoCategoryDataMessage() {
        binding.cardStackView.isVisible = false
        binding.noCategoryData.isVisible = true
    }
    private fun hideNoCategoryDataMessage() {
        binding.cardStackView.isVisible = true
        binding.noCategoryData.isVisible = false
    }
}