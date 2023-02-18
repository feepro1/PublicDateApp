package com.main.chat.di.modules

import com.main.chat.data.firebase.ManageFirebaseMessagesRepositoryImpl
import com.main.chat.data.firebase.ManageMessageRepositoryImpl
import com.main.chat.data.firebase.repository.SendMessageFirebaseRepository
import com.main.chat.data.repository.DeleteMessageRepository
import com.main.chat.data.repository.ReceiveMessageRepository
import com.main.chat.data.repository.SendMessageRepository
import com.main.chat.data.storage.firebase.notification.api.NotificationApi
import com.main.chat.data.storage.firebase.notification.api.NotificationApi.Companion.BASE_URL
import com.main.chat.data.storage.firebase.notification.repository.NotificationRepository
import com.main.chat.data.storage.firebase.notification.repository.NotificationRepositoryImpl
import com.main.chat.data.storage.local.ChatCacheRepository
import com.main.chat.data.storage.local.ChatCacheRepositoryImpl
import com.main.chat.data.storage.local.ChatDao
import com.main.chat.domain.firebase.ManageFirebaseMessagesRepository
import com.main.chat.domain.firebase.ManageMessageRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ChatDataModule(
    private val chatDao: ChatDao
) {

    @Provides
    fun provideManageMessageRepository(
        receiveMessageRepository: ReceiveMessageRepository,
        sendMessageRepository: SendMessageRepository,
        deleteMessageRepository: DeleteMessageRepository
    ): ManageMessageRepository {
        return ManageMessageRepositoryImpl(
            receiveMessageRepository = receiveMessageRepository,
            sendMessageRepository = sendMessageRepository,
            deleteMessageRepository = deleteMessageRepository
        )
    }

    @Provides
    fun provideManageFirebaseMessagesRepository(): ManageFirebaseMessagesRepository {
        return ManageFirebaseMessagesRepositoryImpl()
    }

    @Provides
    fun provideReceiveMessageRepository(
        chatCacheRepository: ChatCacheRepository
    ): ReceiveMessageRepository {
        return ReceiveMessageRepository.Base(chatCacheRepository)
    }

    @Provides
    fun provideSendMessageRepository(
        chatCacheRepository: ChatCacheRepository,
        sendMessageFirebaseRepository: SendMessageFirebaseRepository,
        notificationRepository: NotificationRepository
    ): SendMessageRepository {
        return SendMessageRepository.Base(
            chatCacheRepository = chatCacheRepository,
            sendMessageFirebaseRepository = sendMessageFirebaseRepository,
            notificationRepository = notificationRepository
        )
    }

    @Provides
    fun provideDeleteMessageRepository(
        chatCacheRepository: ChatCacheRepository
    ): DeleteMessageRepository {
        return DeleteMessageRepository.Base(chatCacheRepository)
    }

    @Provides
    fun provideChatCacheRepository(): ChatCacheRepository {
        return ChatCacheRepositoryImpl(chatDao)
    }

    @Provides
    fun provideSendMessageFirebaseRepository(): SendMessageFirebaseRepository {
        return SendMessageFirebaseRepository.Base()
    }

    @Provides
    fun provideNotificationRepository(
        notificationApi: NotificationApi
    ): NotificationRepository {
        return NotificationRepositoryImpl(notificationApi)
    }

    @Provides
    fun provideNotificationRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNotificationApi(
        retrofit: Retrofit
    ): NotificationApi {
        return retrofit.create(NotificationApi::class.java)
    }
}