package com.main.chats.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.main.chats.databinding.FragmentRecyclerViewBinding
import com.main.chats.presentation.adapter.ChatsAdapter
import com.main.chats.presentation.adapter.LikesAdapter

class RecyclerViewFragment(
    private val likesAdapter: LikesAdapter? = null,
    private val chatsAdapter: ChatsAdapter? = null
) : Fragment() {
    private val binding by lazy { FragmentRecyclerViewBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (likesAdapter != null) {
            binding.recyclerView.adapter = likesAdapter
        }
        if (chatsAdapter != null) {
            binding.recyclerView.adapter = chatsAdapter
        }
    }
}