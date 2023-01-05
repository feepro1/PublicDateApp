package com.main.register.presentation.ui.finish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.main.core.base.BaseFragment
import com.main.register.R
import com.main.register.databinding.FragmentFinishRegisterBinding
import com.main.register.di.provider.ProvideRegisterComponent
import com.main.register.presentation.viewmodel.RegisterViewModel
import com.main.register.presentation.viewmodel.RegisterViewModelFactory
import javax.inject.Inject

class FinishRegisterFragment : BaseFragment() {
    private val binding by lazy { FragmentFinishRegisterBinding.inflate(layoutInflater) }
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private val registerViewModel: RegisterViewModel by activityViewModels { registerViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideRegisterComponent).provideRegisterComponent().inject(this)
    }
}