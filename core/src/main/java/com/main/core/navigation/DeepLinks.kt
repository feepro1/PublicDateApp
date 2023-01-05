package com.main.core.navigation

import androidx.core.net.toUri

object DeepLinks {
    val START_REGISTER_DEEP_LINK = "swapLike://startRegister".toUri()
    val FINISH_REGISTER_DEEP_LINK = "swapLike://finishRegister".toUri()
    val LOGIN_DEEP_LINK = "swapLike://login".toUri()
}