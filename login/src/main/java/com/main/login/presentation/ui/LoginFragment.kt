package com.main.login.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.core.base.BaseFragment
import com.main.core.state.ApplicationTextWatcher
import com.main.login.data.entities.LoginData
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

    private val emailWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = loginViewModel.clearEmailError()
    }
    private val passwordWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = loginViewModel.clearPasswordError()
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.checkIsUserSignedIn(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideLoginComponent).provideLoginComponent().inject(this)

        loginViewModel.observeLoginEmailError(this) { inputTextState ->
            inputTextState.apply(binding.etEmail, binding.textInputLayoutEmail)
        }

        loginViewModel.observeLoginPasswordError(this) { inputTextState ->
            inputTextState.apply(binding.etPassword, binding.textInputLayoutPassword)
        }

        binding.btnLogin.setOnClickListener {
            val loginData = LoginData(
                password = binding.etPassword.text.toString().trim(),
                email = binding.etEmail.text.toString().trim()
            )
            loginViewModel.login(loginData, findNavController())
        }

        binding.tvDoNotHaveAnAccount.setOnClickListener {
            loginViewModel.navigateToStartRegisterFragment(findNavController())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etPassword.addTextChangedListener(passwordWatcher)
        binding.etEmail.addTextChangedListener(emailWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.etPassword.removeTextChangedListener(passwordWatcher)
        binding.etEmail.removeTextChangedListener(emailWatcher)
    }
}