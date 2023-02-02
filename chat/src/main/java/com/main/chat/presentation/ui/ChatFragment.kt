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
        (requireActivity().applicationContext as ProvideChatComponent)
            .provideChatComponent().inject(this)
        initDesign()

        binding.btnBack.setOnClickListener {
            chatViewModel.navigateToChatsFragment(findNavController())
        }
        binding.rvMessages.adapter = messageAdapter
        binding.rvMessages.scrollToPosition(messageAdapter.itemCount - 1)

        chatViewModel.observeMessage(this) { message ->
            messageAdapter.map(message)
            binding.rvMessages.scrollToPosition(messageAdapter.itemCount - 1)
        }
        chatViewModel.observeMessages(this) { messages ->
            messageAdapter.mapAll(messages)
        }
        chatViewModel.receiveMessages()

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
        val uid = Firebase.auth.currentUser?.uid.toString()
        val interlocutorUid = coreViewModel.valueChat()?.uid.toString()
        Firebase.firestore.collection(REFERENCE_MESSENGERS).document(uid)
            .collection(REFERENCE_CHATS).document()
            .collection(REFERENCE_MESSAGES).document(interlocutorUid)
            .addSnapshotListener { value, error ->
                Log.d("MyLog", "ChatFragment, value: ${value.toString()}")
                //todo add to database, check logic
            }
    }

    @SuppressLint("SetTextI18n")
    private fun initDesign() {
        val user = coreViewModel.valueChat()
        Glide.with(requireContext()).load(user?.avatarUrl).into(binding.ivUserAvatar)
        binding.tvUsername.text = "${user?.firstName} ${user?.lastName}"
    }
}