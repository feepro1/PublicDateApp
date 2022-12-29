package com.main.login.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.core.base.BaseFragment
import com.main.login.R
import com.main.login.databinding.FragmentLoginBinding
import com.main.login.di.provider.ProvideLoginComponent
import com.main.login.presentation.viewmodel.LoginViewModel
import com.main.login.presentation.viewmodel.LoginViewModelFactory
import javax.inject.Inject

class LoginFragment : BaseFragment() {
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private val loginViewModel: LoginViewModel by activityViewModels { loginViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideLoginComponent).provideLoginComponent().inject(this)
    }

}