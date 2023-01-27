package com.main.likes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.main.core.base.BaseFragment
import com.main.core.entities.Like
import com.main.core.entities.User
import com.main.core.toast.showErrorColorToast
import com.main.likes.databinding.FragmentLikesBinding
import com.main.likes.di.provider.ProvideLikesComponent
import com.main.likes.presentation.adapter.LikeCLickListener
import com.main.likes.presentation.adapter.LikesAdapter
import com.main.likes.presentation.viewmodel.LikesViewModel
import com.main.likes.presentation.viewmodel.LikesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class LikesFragment : BaseFragment() {
    private val binding by lazy { FragmentLikesBinding.inflate(layoutInflater) }
    @Inject
    lateinit var likesViewModelFactory: LikesViewModelFactory
    private val likesViewModel: LikesViewModel by activityViewModels { likesViewModelFactory }

    private val likesAdapter = LikesAdapter(object: LikeCLickListener {
        override fun iconClick(like: Like) {
            lifecycleScope.launch {
                likesViewModel.likeUser(
                    User(
                        firstName = like.firstName, lastName = like.lastName, age = like.age,
                        avatarUrl = like.avatarUrl, city = like.city, uid = like.uid
                    )
                )
            }
        }
        override fun buttonWriteClick(like: Like) = Unit
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideLikesComponent).provideLikesComponent().inject(this)

        binding.rvLikes.adapter = likesAdapter

        likesViewModel.observeMotionToastError(this) { error ->
            showErrorColorToast(this, error)
        }

        likesViewModel.observeLikes(this) { likes ->
            likesAdapter.mapAll(likes)
        }

        likesViewModel.getAllLikes()
    }
}