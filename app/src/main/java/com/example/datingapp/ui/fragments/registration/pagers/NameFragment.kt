package com.example.datingapp.ui.fragments.registration.pagers

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.datingapp.R
import com.example.datingapp.databinding.FragmentNameBinding
import com.example.datingapp.ui.fragments.base.BaseFragment
import com.example.datingapp.ui.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : BaseFragment<FragmentNameBinding>() {

    private val viewModel by activityViewModels<RegistrationViewModel>()
    override fun getViewBinding() = FragmentNameBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.nameEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var text = p0.toString()
                if(text.contains(" ")) {
                    text = text.replace(" ", "")
                    binding.nameEditText.setText(text)
                    binding.nameEditText.setSelection(text.length)
                }

                if(validateName(text)) {
                    viewModel.setActiveButton(true)
                } else {
                    viewModel.setActiveButton(false)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun validateName(name: String): Boolean {
        val result = name.trim()
        if(result.isEmpty()) return false
        if(result.contains(" ")) return false
        if(!result[0].isUpperCase()) return false
        if(result.length > 30) return false
        return true
    }
}
