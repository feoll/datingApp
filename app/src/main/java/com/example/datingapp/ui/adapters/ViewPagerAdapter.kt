package com.example.datingapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(
    private val list: List<Fragment>,
    private val manager: FragmentManager,
    private val lifecycle: Lifecycle
): FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}