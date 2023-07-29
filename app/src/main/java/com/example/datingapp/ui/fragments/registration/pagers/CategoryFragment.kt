package com.example.datingapp.ui.fragments.registration.pagers

import android.os.Bundle
import android.view.View
import com.example.datingapp.databinding.FragmentCategoryBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.utils.setupChipToGroup


class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    private val title: String? by lazy { arguments?.getString(KEY_TITLE) }
    private val isSingleSelection: Boolean? by lazy { arguments?.getBoolean(KEY_SELECTION) }
    private val categories: ArrayList<String>? by lazy { arguments?.getStringArrayList(KEY_CATEGORIES) }
    override fun getViewBinding() = FragmentCategoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillUI()
    }

    private fun fillUI() {
        title?.let {
            binding.categoryTitle.text = it
        }
        isSingleSelection?.let {
            binding.categoryChipGroup.isSingleSelection = it
        }
        categories?.let {
            setupChipToGroup(it.toList(), binding.categoryChipGroup)
        }
    }

    companion object {
        private const val KEY_TITLE = "category_fragment_title"
        private const val KEY_CATEGORIES = "category_fragment_categories"
        private const val KEY_SELECTION = "category_fragment_isSingleSelection"
        fun newInstance(title: String, categories: ArrayList<String>, isSingleSelection: Boolean): CategoryFragment {
            val instance = CategoryFragment()
            val args = Bundle()
            args.putBoolean(KEY_SELECTION, isSingleSelection)
            args.putStringArrayList(KEY_CATEGORIES, categories)
            args.putString(KEY_TITLE, title)
            instance.arguments = args
            return instance
        }
    }
}