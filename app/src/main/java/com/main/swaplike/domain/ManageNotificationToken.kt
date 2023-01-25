package com.main.swaplike.domain

interface ManageNotificationToken {

    fun updateToken()

    fun updateToken(token: String)
}