package com.main.chats.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.main.chats.data.entities.Chat
import com.main.chats.data.entities.LikeFromUser
import com.main.chats.presentation.ui.RecyclerViewFragment
import com.main.chats.presentation.viewmodel.ChatsViewModel
import com.main.core.viewmodel.CoreViewModel

class FragmentAdapter(
    fragment: FragmentActivity,
    private val list: List<String>,
    private val chatsViewModel: ChatsViewModel,
    private val navController: NavController,
    private val coreViewModel: CoreViewModel
): FragmentStateAdapter(fragment)  {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return RecyclerViewFragment(chatsAdapter = ChatsAdapter(object : ChatCLickListener {

                override fun iconClick(chat: Chat) {
                    TODO("Not yet implemented")
                }

                override fun itemClick(chat: Chat) {
                    coreViewModel.manageChat(chat.mapToCoreChat())
                    chatsViewModel.navigateToChat(navController)
                }
            }))
        }
        if (position == 1) {
            return RecyclerViewFragment(likesAdapter = LikesAdapter(object : LikeCLickListener {

                override fun iconClick(likeFromUser: LikeFromUser) {
                    TODO("Not yet implemented")
                }

                override fun itemClick(likeFromUser: LikeFromUser) {
                    TODO("Not yet implemented")
                }
            }))
        }
        return RecyclerViewFragment()
    }
}