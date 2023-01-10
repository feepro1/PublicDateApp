package com.main.core.navigation

import androidx.core.net.toUri

object DeepLinks {
    val START_REGISTER_DEEP_LINK = "swapLike://register/start".toUri()
    val FINISH_REGISTER_DEEP_LINK = "swapLike://register/finish".toUri()
    val LOGIN_DEEP_LINK = "swapLike://login".toUri()
    val DATING_DEEP_LINK = "swapLike://dating".toUri()
}