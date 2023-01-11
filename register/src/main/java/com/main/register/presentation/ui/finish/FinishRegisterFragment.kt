package com.main.register.presentation.ui.finish

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.core.base.BaseFragment
import com.main.core.state.ApplicationTextWatcher
import com.main.core.toast.showColorToast
import com.main.register.R
import com.main.register.data.entities.RegisterData
import com.main.register.databinding.FragmentFinishRegisterBinding
import com.main.register.di.provider.ProvideRegisterComponent
import com.main.register.presentation.viewmodel.RegisterViewModel
import com.main.register.presentation.viewmodel.RegisterViewModelFactory
import javax.inject.Inject
import kotlin.properties.Delegates

class FinishRegisterFragment : BaseFragment() {
    private val binding by lazy { FragmentFinishRegisterBinding.inflate(layoutInflater) }
    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private val registerViewModel: RegisterViewModel by activityViewModels { registerViewModelFactory }
    private var launcher by Delegates.notNull<ActivityResultLauncher<Intent>>()

    private val firstNameWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearFirstNameError()
    }
    private val lastNameWatcher = object : ApplicationTextWatcher() {
        override fun afterTextChanged(s: Editable?) = registerViewModel.clearLastNameError()
    }

    override fun onStart() {
        super.onStart()
        registerViewModel.checkIsUserConfirmedEmail(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideRegisterComponent).provideRegisterComponent().inject(this)

        registerViewModel.observeMotionToastText(this) { text ->
            showColorToast(this, text)
        }

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
                    lastName = binding.etLastName.text.toString().trim(),
                    avatar = (binding.ivUserAvatar.drawable).toBitmap()
                ) ?: RegisterData(),
                findNavController()
            ) { showColorToast(this, getString(R.string.sent_email_verification)) }
        }

        binding.ivUserAvatar.setOnClickListener {
            registerViewModel.onClickChooseImage(launcher)
        }

        binding.tvHaveAnAccount.setOnClickListener {
            registerViewModel.navigateToLoginFragment(findNavController())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val dataUri = result.data?.data
                binding.ivUserAvatar.setImageURI(dataUri)
            }
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