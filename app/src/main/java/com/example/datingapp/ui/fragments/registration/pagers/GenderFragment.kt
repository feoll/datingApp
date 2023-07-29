package com.example.datingapp.ui.fragments.registration.pagers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentGenderBinding
import com.example.datingapp.ui.fragments.base.BaseFragment


class GenderFragment : BaseFragment<FragmentGenderBinding>() {
    override fun getViewBinding() = FragmentGenderBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            checkboxMan.setOnClickListener {
                checkboxMan.isChecked = true
                checkboxWoman.isChecked = false
            }
            checkboxWoman.setOnClickListener {
                checkboxWoman.isChecked = true
                checkboxMan.isChecked = false
            }
        }
    }
}