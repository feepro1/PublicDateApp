package com.main.chats.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.main.chats.R
import com.main.chats.databinding.FragmentChatsBinding
import com.main.chats.di.provider.ProvideChatsComponent
import com.main.chats.presentation.viewmodel.ChatsViewModel
import com.main.chats.presentation.viewmodel.ChatsViewModelFactory
import javax.inject.Inject

class ChatsFragment : Fragment() {
    private val binding by lazy { FragmentChatsBinding.inflate(layoutInflater) }
    @Inject
    lateinit var chatsViewModelFactory: ChatsViewModelFactory
    private val chatViewModel: ChatsViewModel by activityViewModels { chatsViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideChatsComponent).provideChatsComponent().inject(this)

        binding.mainBottomNavigationView.menu.getItem(0).isChecked = true

        binding.mainBottomNavigationView.setOnItemSelectedListener { menuItem ->
            chatViewModel.manageMenuItem(menuItem, findNavController())
        }
    }
}