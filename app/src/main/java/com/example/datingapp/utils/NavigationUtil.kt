package com.example.datingapp.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigate(directions: NavDirections) {
    val navController = findNavController()
    when (val destination = navController.currentDestination) {
        is FragmentNavigator.Destination -> {
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            }
        }
        is DialogFragmentNavigator.Destination -> {
            if (javaClass.name == destination.className) {
                navController.navigate(directions)
            }
        }
    }
}

fun closeKeyboard(fragmentActivity: FragmentActivity) {
    val view = fragmentActivity.currentFocus
    if(view != null) {
        val imm = fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}