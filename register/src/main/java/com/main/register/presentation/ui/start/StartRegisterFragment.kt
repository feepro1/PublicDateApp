package com.main.register.presentation.ui.start

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.core.base.BaseFragment
import com.main.core.exception.FirstNameException
import com.main.core.state.ApplicationTextWatcher
import com.main.register.data.entities.RegisterData
import com.main.register.databinding.FragmentStartRegisterBinding
import com.main.register.di.provider.ProvideRegisterComponent
import com.main.register.presentation.viewmodel.RegisterViewModel
import com.main.register.presentation.viewmodel.RegisterViewModelFactory
import javax.inject.Inject

class StartRegisterFragment : BaseFragment() {
    private val binding by lazy { FragmentStartRegisterBinding.inflate(layoutInflater) }
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private val registerViewModel: RegisterViewModel by activityViewModels { registerViewModelFactory }

    private val emailWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearEmailError()
    }
    private val passwordWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearPasswordError()
    }
    private val confirmPasswordWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearConfirmPasswordError()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideRegisterComponent).provideRegisterComponent().inject(this)

        registerViewModel.observeRegisterEmailError(this) { inputTextState ->
            inputTextState.apply(binding.etEmail, binding.textInputLayoutEmail)
        }

        registerViewModel.observeRegisterPasswordError(this) { inputTextState ->
            inputTextState.apply(binding.etPassword, binding.textInputLayoutPassword)
        }

        registerViewModel.observeRegisterConfirmPasswordError(this) { inputTextState ->
            inputTextState.apply(binding.etConfirmPassword, binding.textInputLayoutConfirmPassword)
        }

        binding.btnNext.setOnClickListener {
            registerViewModel.mapRegisterData(
                RegisterData(
                    email = binding.etEmail.text.toString().trim(),
                    password = binding.etPassword.text.toString().trim(),
                    confirmPassword = binding.etConfirmPassword.text.toString().trim()
                )
            )
            registerViewModel.navigateToFinishRegisterFragment(findNavController())
        }

        binding.tvHaveAnAccount.setOnClickListener {
            registerViewModel.navigateToLoginFragment(findNavController())
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etEmail.addTextChangedListener(emailWatcher)
        binding.etPassword.addTextChangedListener(passwordWatcher)
        binding.etConfirmPassword.addTextChangedListener(confirmPasswordWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.etEmail.removeTextChangedListener(emailWatcher)
        binding.etPassword.removeTextChangedListener(passwordWatcher)
        binding.etConfirmPassword.removeTextChangedListener(confirmPasswordWatcher)
    }
}