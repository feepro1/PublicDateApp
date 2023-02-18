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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.main.chat.data.storage.local.MessageCacheModel
import com.main.chat.databinding.FragmentChatBinding
import com.main.chat.di.provider.ProvideChatComponent
import com.main.chat.presentation.adapter.MessagesAdapter
import com.main.chat.presentation.viewmodel.ChatViewModel
import com.main.chat.presentation.viewmodel.ChatViewModelFactory
import com.main.core.base.BaseFragment
import com.main.core.firebase.FirebaseConstants.REFERENCE_CHATS
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSAGES
import com.main.core.firebase.FirebaseConstants.REFERENCE_MESSENGERS
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
    private val messageAdapter = MessagesAdapter()

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
        binding.rvMessages.adapter = messageAdapter
        binding.rvMessages.scrollToPosition(messageAdapter.itemCount - 1)
        binding.btnSendMessage.setOnClickListener {
            chatViewModel.sendMessage(
                MessageCacheModel(
                    message = binding.etMessage.text.toString().trim(),
                    senderUid = Firebase.auth.currentUser?.uid.toString(),
                    receiverUid = coreViewModel.valueChat()?.uid.toString(),
                    dateTimeMillis = System.currentTimeMillis()
                )
            )
            binding.etMessage.text.clear()
        }

        chatViewModel.observeMessage(this) { message ->
            messageAdapter.map(message)
            binding.rvMessages.scrollToPosition(messageAdapter.itemCount - 1)
        }
        chatViewModel.observeMessages(this) { messages ->
            messageAdapter.mapAll(messages)
        }
        chatViewModel.observeMessagesWithoutClear(this) { messages ->
            messageAdapter.mapAllWithoutClear(messages)
        }
        chatViewModel.receiveMessages()
        chatViewModel.receiveMessageRealtime(coreViewModel.valueChat()?.uid.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun initDesign() {
        val user = coreViewModel.valueChat()
        Glide.with(requireContext()).load(user?.avatarUrl).into(binding.ivUserAvatar)
        binding.tvUsername.text = "${user?.firstName} ${user?.lastName}"
    }

    override fun onDestroy() {
        super.onDestroy()
        chatViewModel.valueListenerRegistration()?.remove()
    }
}