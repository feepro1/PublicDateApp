package com.main.register.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.main.core.base.BaseFragment
import com.main.register.databinding.FragmentStartRegisterBinding

class StartRegisterFragment: BaseFragment() {
    private val binding by lazy { FragmentStartRegisterBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}