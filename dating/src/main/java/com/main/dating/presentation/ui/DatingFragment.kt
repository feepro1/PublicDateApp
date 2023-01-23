package com.main.dating.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.core.base.BaseFragment
import com.main.core.toast.showErrorColorToast
import com.main.dating.data.entities.User
import com.main.dating.databinding.FragmentDatingBinding
import com.main.dating.di.provider.ProvideDatingComponent
import com.main.dating.presentation.adapter.ManageUserClickListener
import com.main.dating.presentation.adapter.SwipeCardAdapter
import com.main.dating.presentation.viewmodel.DatingViewModel
import com.main.dating.presentation.viewmodel.DatingViewModelFactory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import javax.inject.Inject

class DatingFragment : BaseFragment() {
    private val binding by lazy { FragmentDatingBinding.inflate(layoutInflater) }
    @Inject
    lateinit var datingViewModelFactory: DatingViewModelFactory
    private val datingViewModel: DatingViewModel by activityViewModels { datingViewModelFactory }
    private lateinit var layoutManager: CardStackLayoutManager

    private val manageUserClickListener = object : ManageUserClickListener {
        override fun clickLike(user: User) {
            datingViewModel.swipeLike(binding.cardStackView, layoutManager)
        }
        override fun clickDislike(user: User) {
            datingViewModel.swipeDislike(binding.cardStackView, layoutManager)
        }
    }
    private val cardStackListener = object : CardStackListener {
        override fun onCardSwiped(direction: Direction?) {
            datingViewModel.valueUser()?.let { user ->
                datingViewModel.manageDirection(direction, user)
            }
        }
        override fun onCardDisappeared(view: View?, position: Int) {
            datingViewModel.manageUser(swipeCardAdapter.users[position])
        }
        override fun onCardDragging(direction: Direction?, ratio: Float) = Unit
        override fun onCardRewound() = Unit
        override fun onCardCanceled() = Unit
        override fun onCardAppeared(view: View?, position: Int) = Unit
    }
    private val swipeCardAdapter = SwipeCardAdapter(manageUserClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideDatingComponent).provideDatingComponent().inject(this)

        layoutManager = datingViewModel.initCardStackLayoutManager(context, cardStackListener)
        binding.mainBottomNavigationView.menu.getItem(1).isChecked = true
        binding.cardStackView.layoutManager = layoutManager
        binding.cardStackView.adapter = swipeCardAdapter

        datingViewModel.observeMotionToastError(this) { text ->
            showErrorColorToast(this, text)
        }

        datingViewModel.observeUsersList(this) { users ->
            swipeCardAdapter.mapAll(users)
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            datingViewModel.manageMenuItem(menuItem, findNavController())
        }
    }
}