package com.main.profile.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.main.core.base.BaseFragment
import com.main.core.toast.showErrorColorToast
import com.main.profile.data.entities.UserInfoLocal
import com.main.profile.di.provider.ProvideProfileComponent
import com.main.profile.presentation.viewmodel.ProfileViewModel
import com.main.profile.presentation.viewmodel.ProfileViewModelFactory
import javax.inject.Inject
import kotlin.properties.Delegates

class ProfileFragment : BaseFragment() {
    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private val profileViewModel: ProfileViewModel by activityViewModels { profileViewModelFactory }
    private var launcher by Delegates.notNull<ActivityResultLauncher<Intent>>()

    private val seekBarListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            binding.tvAge.text = "Age: $progress"
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideProfileComponent).provideProfileComponent()
            .inject(this)

        binding.mainBottomNavigationView.menu.getItem(2).isChecked = true

        bindProgressButton(binding.btnSave)
        profileViewModel.receiveUserInfo()

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            profileViewModel.manageMenuItem(menuItem, findNavController())
        }

        binding.ivUserAvatar.setOnClickListener {
            profileViewModel.onClickChooseImage(launcher)
        }

        profileViewModel.observeMotionToastText(this) { text ->
            showErrorColorToast(this, text)
        }

        binding.sbAge.setOnSeekBarChangeListener(seekBarListener)

        profileViewModel.observeUserInfo(this) { user ->
            binding.etFirstName.setText(user.firstName)
            binding.etLastName.setText(user.lastName)
            binding.etCity.setText(user.city)
            binding.etRegion.setText(user.region)
            binding.etAboutMe.setText(user.aboutMe)
            Glide.with(this).load(user.avatarUrl).into(binding.ivUserAvatar)
            binding.tvAge.text = "Age: ${user?.age ?: "unknow"}"
            binding.sbAge.progress = user.age ?: 0
        }

        binding.btnSave.setOnClickListener {
            val userInfoLocal = UserInfoLocal(
                firstName = binding.etFirstName.text.toString().trim(),
                lastName = binding.etLastName.text.toString().trim(),
                avatar = (binding.ivUserAvatar.drawable).toBitmap(),
                email = profileViewModel.valueUserInfo()?.email.toString(),
                age = binding.sbAge.progress,
                city = binding.etCity.text.toString().trim(),
                region = binding.etRegion.text.toString().trim(),
                aboutMe = binding.etAboutMe.text.toString().trim(),
                uid = Firebase.auth.currentUser?.uid.toString(),
                token = profileViewModel.valueUserInfo()?.token.toString()
            )
            profileViewModel.saveUserInfo(
                userInfoLocal,
                { binding.btnSave.hideProgress(R.string.submit) },
                { binding.btnSave.hideProgress(R.string.failure) },
                { binding.btnSave.hideProgress(R.string.save) }
            )
            binding.btnSave.showProgress {
                buttonTextRes = R.string.loading
                progressColor = Color.WHITE
            }
        }

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val dataUri = result.data?.data
                    binding.ivUserAvatar.setImageURI(dataUri)
                }
            }
    }
}