package com.main.chat.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.databinding.FragmentChatBinding
import com.main.chat.di.provider.ProvideChatComponent
import com.main.chat.presentation.viewmodel.ChatViewModel
import com.main.chat.presentation.viewmodel.ChatViewModelFactory
import com.main.core.base.BaseFragment
import com.main.core.viewmodel.CoreViewModel
import com.main.core.viewmodel.CoreViewModelFactory
import javax.inject.Inject

class ChatFragment : BaseFragment() {
    private val binding by lazy { FragmentChatBinding.inflate(layoutInflater) }
    @Inject
    lateinit var chatViewModelFactory: ChatViewModelFactory
    private val chatViewModel: ChatViewModel by activityViewModels { chatViewModelFactory }
    @Inject
    lateinit var coreViewModelFactory: CoreViewModelFactory
    private val coreViewModel: CoreViewModel by activityViewModels { coreViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().applicationContext as ProvideChatComponent).provideChatComponent().inject(this)
        initDesign()

        binding.btnBack.setOnClickListener {
            chatViewModel.navigateToChatsFragment(findNavController())
        }

        chatViewModel.observeMessages(this) {
            Log.d("MyLog", it.joinToString())
        }
        chatViewModel.receiveMessages()
    }

    @SuppressLint("SetTextI18n")
    private fun initDesign() {
        val user = coreViewModel.valueChat()
        Glide.with(requireContext()).load(user?.avatarUrl).into(binding.ivUserAvatar)
        binding.tvUsername.text = "${user?.firstName} ${user?.lastName}"
    }
}