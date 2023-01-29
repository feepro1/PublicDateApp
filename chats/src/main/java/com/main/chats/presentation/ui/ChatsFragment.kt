package com.main.chats.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.chats.databinding.FragmentChatsBinding
import com.main.chats.di.provider.ProvideChatsComponent
import com.main.chats.presentation.adapter.ChatCLickListener
import com.main.chats.presentation.adapter.ChatsAdapter
import com.main.chats.presentation.viewmodel.ChatsViewModel
import com.main.chats.presentation.viewmodel.ChatsViewModelFactory
import com.main.core.base.BaseFragment
import com.main.core.base.entity.Chat
import com.main.core.viewmodel.CoreViewModel
import com.main.core.viewmodel.CoreViewModelFactory
import javax.inject.Inject

class ChatsFragment : BaseFragment() {
    private val binding by lazy { FragmentChatsBinding.inflate(layoutInflater) }
    @Inject
    lateinit var chatsViewModelFactory: ChatsViewModelFactory
    private val chatsViewModel: ChatsViewModel by activityViewModels { chatsViewModelFactory }
    @Inject
    lateinit var coreViewModelFactory: CoreViewModelFactory
    private val coreViewModel: CoreViewModel by activityViewModels { coreViewModelFactory }

    private val chatsAdapter = ChatsAdapter(object : ChatCLickListener {
        override fun itemClick(chat: Chat) {
            coreViewModel.manageChat(chat)
            chatsViewModel.navigateToChat(findNavController())
        }
        override fun iconClick(chat: Chat) = Unit
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideChatsComponent).provideChatsComponent().inject(this)

        binding.mainBottomNavigationView.menu.getItem(0).isChecked = true
        binding.rvChats.adapter = chatsAdapter

        chatsViewModel.getAllChats()

        chatsViewModel.observeChats(this) { chats ->
            chatsAdapter.mapAll(chats)
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            chatsViewModel.manageMenuItem(menuItem, findNavController())
        }
    }
}