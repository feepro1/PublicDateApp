package com.main.register.presentation.ui.finish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.main.core.base.BaseFragment
import com.main.register.R
import com.main.register.databinding.FragmentFinishRegisterBinding

class FinishRegisterFragment : BaseFragment() {
    private val binding by lazy { FragmentFinishRegisterBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}