package com.example.datingapp.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.datingapp.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun setupChipToGroup(list: List<String>, group: ChipGroup): Boolean {
    if(list.isEmpty()) {
        return false
    }
    list.forEach {
        val chip = LayoutInflater.from(group.context).inflate(
            R.layout.profile_edit_chip,
            group,
            false
        ) as Chip
        chip.text = it
        group.addView(chip)
    }
    return true
}
