package com.main.profile.presentation.ui

import android.content.Intent
import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.example.profile.databinding.FragmentProfileBinding
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

        binding.ivUserAvatar.setOnClickListener {
            profileViewModel.onClickChooseImage(launcher)
        }

        profileViewModel.observeMotionToastText(this) { text ->
            showErrorColorToast(this, text)
        }

        profileViewModel.observeUserInfo(this) { userInfo ->
            binding.etFirstName.setText(userInfo.firstName)
            binding.etLastName.setText(userInfo.lastName)
            binding.etEmal.setText(userInfo.email)
            binding.etCity.setText(userInfo.city)
            binding.etRegion.setText(userInfo.region)
            binding.etAboutMe.setText(userInfo.aboutMe)
            Glide.with(this).load(userInfo.avatarUrl).into(binding.ivUserAvatar)
            binding.tvAge.text = "Age: ${userInfo.age}"
        }

        binding.btnSave.setOnClickListener {
            val userInfoLocal = UserInfoLocal(
                firstName = binding.etFirstName.text.toString().trim(),
                lastName = binding.etLastName.text.toString().trim(),
                avatar = (binding.ivUserAvatar.drawable).toBitmap(),
                email = binding.etEmal.text.toString().trim(),
                age = 1,
                city = binding.etCity.text.toString().trim(),
                region = binding.etRegion.text.toString().trim(),
                aboutMe = binding.etAboutMe.text.toString().trim()
            )
            profileViewModel.saveUserInfo(userInfoLocal)
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val dataUri = result.data?.data
                binding.ivUserAvatar.setImageURI(dataUri)
            }
        }
    }
}