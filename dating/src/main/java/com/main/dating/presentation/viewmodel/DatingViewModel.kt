package com.main.dating.presentation.viewmodel

import android.content.Context
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.main.core.DispatchersList
import com.main.core.exception.NetworkException
import com.main.dating.data.entities.User
import com.main.dating.domain.ManageCommunication
import com.main.dating.domain.ManageDirection
import com.main.dating.domain.ProvideAnimationsSettings
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.domain.navigation.DatingNavigation
import com.main.dating.presentation.communication.DatingCommunication
import com.main.dating.presentation.communication.ObserveDatingCommunications
import com.main.dating.presentation.communication.ValueDatingCommunications
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.launch

class DatingViewModel(
    private val datingInteractor: DatingInteractor,
    private val datingCommunication: DatingCommunication,
    private val dispatchers: DispatchersList,
    private val manageDirection: ManageDirection,
    private val provideAnimationsSettings: ProvideAnimationsSettings,
    private val datingNavigation: DatingNavigation
) : ViewModel(), ObserveDatingCommunications, ManageCommunication, ValueDatingCommunications {

    init {
        getUsersFromDatabase()
    }

    fun getUsersFromDatabase() {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.getUsersFromDatabase()
            if (result.data != null) {
                result.data?.let { datingCommunication.manageUsersList(it) }
            }
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    fun likeUser(user: User) {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.likeUser(user)
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    fun dislikeUser(user: User) {
        viewModelScope.launch(dispatchers.io()) {
            val result = datingInteractor.dislikeUser(user)
            if (result.exception is NetworkException) {
                val message = (result.exception as NetworkException).message.toString()
                datingCommunication.manageMotionToastText(message)
            }
        }
    }

    fun manageDirection(direction: Direction?, user: User) {
        manageDirection.manage(
            direction = direction,
            like = { likeUser(user) },
            dislike = { dislikeUser(user) }
        )
    }

    fun initCardStackLayoutManager(context: Context?, cardStackListener: CardStackListener): CardStackLayoutManager {
        val layoutManager = CardStackLayoutManager(context, cardStackListener)
        layoutManager.setCanScrollVertical(false)
        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setTranslationInterval(6.0f)
        return layoutManager
    }

    fun swipeLike(cardStackView: CardStackView, layoutManager: CardStackLayoutManager) {
        provideAnimationsSettings.createAnimationsSettingsForLike(cardStackView, layoutManager)
    }

    fun swipeDislike(cardStackView: CardStackView, layoutManager: CardStackLayoutManager) {
        provideAnimationsSettings.createAnimationsSettingsForDislike(cardStackView, layoutManager)
    }

    fun manageMenuItem(menuItem: MenuItem, navController: NavController): Boolean {
        when (menuItem.itemId) {
            com.main.core.R.id.itemProfile -> datingNavigation.navigateToProfileFragment(navController)
            com.main.core.R.id.itemChats -> datingNavigation.navigateToChatsFragment(navController)
        }
        return true
    }

    fun navigateToLikesFragment(navController: NavController) {
        datingNavigation.navigateToLikesFragment(navController)
    }

    override fun observeMotionToastError(owner: LifecycleOwner, observer: Observer<String>) {
        datingCommunication.observeMotionToastError(owner, observer)
    }

    override fun observeUsersList(owner: LifecycleOwner, observer: Observer<List<User>>) {
        datingCommunication.observeUsersList(owner, observer)
    }

    override fun manageUser(user: User) {
        datingCommunication.manageUser(user)
    }

    override fun valueUser() = datingCommunication.valueUser()
}