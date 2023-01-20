package com.main.chats.domain

import com.main.chats.data.entities.LikeFromUser

interface ManageLikesAdapterData {

    fun mapAll(newLikes: List<LikeFromUser>)
}