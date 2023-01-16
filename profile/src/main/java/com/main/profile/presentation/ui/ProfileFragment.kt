package com.main.profile.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.main.profile.di.provider.ProvideProfileComponent
import com.main.profile.presentation.viewmodel.ProfileViewModel
import com.main.profile.presentation.viewmodel.ProfileViewModelFactory
import com.main.core.base.BaseFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private val profileViewModel: ProfileViewModel by activityViewModels { profileViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideProfileComponent).provideProfileComponent().inject(this)

        binding.mainBottomNavigationView.menu.getItem(2).isChecked = true

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            profileViewModel.manageMenuItem(menuItem, findNavController())
        }
    }
}