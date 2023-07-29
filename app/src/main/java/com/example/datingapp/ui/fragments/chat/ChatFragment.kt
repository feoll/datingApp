package com.example.datingapp.ui.fragments.chat

import android.os.Bundle
import android.view.View
import com.example.datingapp.databinding.FragmentChatBinding
import com.example.datingapp.ui.fragments.base.BaseFragment


class ChatFragment: BaseFragment<FragmentChatBinding>() {
    override fun getViewBinding() = FragmentChatBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}