package com.main.chat.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.main.chat.R
import com.main.chat.databinding.FragmentChatBinding
import com.main.chat.di.provider.ProvideChatComponent
import com.main.chat.presentation.viewmodel.ChatViewModel
import com.main.chat.presentation.viewmodel.ChatViewModelFactory
import com.main.core.base.BaseFragment
import javax.inject.Inject

class ChatFragment : BaseFragment() {
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater) }
    @Inject
    lateinit var chatViewModelFactory: ChatViewModelFactory
    private val chatViewModel: ChatViewModel by activityViewModels { chatViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideChatComponent).provideChatComponent().inject(this)
    }
}