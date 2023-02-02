package com.main.likes.domain

import com.main.core.entities.Like

interface ManageLikesAdapterData {

    fun mapAll(newLikes: List<Like>)

    fun setFirstLike(like: Like)
}