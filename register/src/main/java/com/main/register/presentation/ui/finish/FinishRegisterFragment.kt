package com.main.register.presentation.ui.finish

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.main.core.base.BaseFragment
import com.main.core.state.ApplicationTextWatcher
import com.main.register.R
import com.main.register.data.entities.RegisterData
import com.main.register.databinding.FragmentFinishRegisterBinding
import com.main.register.di.provider.ProvideRegisterComponent
import com.main.register.presentation.viewmodel.RegisterViewModel
import com.main.register.presentation.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FinishRegisterFragment : BaseFragment() {
    private val binding by lazy { FragmentFinishRegisterBinding.inflate(layoutInflater) }
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private val registerViewModel: RegisterViewModel by activityViewModels { registerViewModelFactory }

    private val firstNameWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearFirstNameError()
    }
    private val lastNameWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearLastNameError()
    }

    override fun onStart() {
        super.onStart()
        registerViewModel.checkIsUserConfirmedEmail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideRegisterComponent).provideRegisterComponent().inject(this)

        registerViewModel.observeRegisterFirstNameError(this) { inputTextState ->
            inputTextState.apply(binding.etFirstName, binding.textInputLayoutFirstName)
        }

        registerViewModel.observeRegisterLastNameError(this) { inputTextState ->
            inputTextState.apply(binding.etLastName, binding.textInputLayoutLastName)
        }

        binding.btnRegister.setOnClickListener {
            val startRegisterData = registerViewModel.valueRegisterData()
            registerViewModel.register(
                startRegisterData?.copy(
                    firstName = binding.etFirstName.text.toString().trim(),
                    lastName = binding.etLastName.text.toString().trim()
                ) ?: RegisterData()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etFirstName.addTextChangedListener(firstNameWatcher)
        binding.etLastName.addTextChangedListener(lastNameWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.etFirstName.removeTextChangedListener(firstNameWatcher)
        binding.etLastName.removeTextChangedListener(lastNameWatcher)
    }
}