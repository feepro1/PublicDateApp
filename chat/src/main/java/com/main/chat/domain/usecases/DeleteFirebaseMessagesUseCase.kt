package com.main.chat.domain.usecases

import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.core.Resource

class DeleteFirebaseMessagesUseCase(
    private val manageFirebaseMessagesRepository: ManageFirebaseMessagesRepository
) {

    suspend fun execute(): Resource<Boolean> {
        return manageFirebaseMessagesRepository.deleteAllMessages()
    }
}