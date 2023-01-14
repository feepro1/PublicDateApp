package com.main.dating.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.core.base.BaseFragment
import com.main.dating.databinding.FragmentDatingBinding
import com.main.dating.di.provider.ProvideDatingComponent

class DatingFragment : BaseFragment() {
    private val binding by lazy { FragmentDatingBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideDatingComponent).provideDatingComponent().inject(this)

        binding.button.setOnClickListener {
            Firebase.auth.signOut()
        }
    }
}