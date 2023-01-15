package com.main.dating.domain

import com.yuyakaido.android.cardstackview.Direction

interface ManageDirection {

    fun manage(direction: Direction?, like: () -> (Unit), dislike: () -> (Unit))

    class Base : ManageDirection {

        override fun manage(direction: Direction?, like: () -> Unit, dislike: () -> Unit) {
            if (direction == Direction.Right) like.invoke()
            if (direction == Direction.Left) dislike.invoke()
        }
    }
}