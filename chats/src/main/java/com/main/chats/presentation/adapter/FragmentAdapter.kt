package com.main.chats.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.main.chats.presentation.ui.RecyclerViewFragment

class FragmentAdapter(
    fragment: FragmentActivity,
    private val list: List<String>
): FragmentStateAdapter(fragment)  {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return RecyclerViewFragment(chatsAdapter = ChatsAdapter())
        }
        if (position == 1) {
            return RecyclerViewFragment(likesAdapter = LikesAdapter())
        }
        return RecyclerViewFragment()
    }
}