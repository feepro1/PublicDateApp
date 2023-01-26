package com.main.likes.domain

import com.main.likes.data.entities.LikeFromUser

interface ManageLikesAdapterData {

    fun mapAll(newLikes: List<LikeFromUser>)
}