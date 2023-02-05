package com.main.chats.domain

import androidx.fragment.app.FragmentActivity
import com.main.core.entities.Chat

interface ManageBottomSheetDialog {

    fun showDialog(activity: FragmentActivity, chat: Chat)
}