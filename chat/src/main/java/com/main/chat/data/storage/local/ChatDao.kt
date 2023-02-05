package com.main.chat.data.storage.local

import androidx.room.*

@Entity
@Dao
interface ChatDao {

    @Insert
    fun addMessage(messageCacheModel: MessageCacheModel)

    @Delete
    fun deleteMessage(messageCacheModel: MessageCacheModel)

    @Delete
    fun deleteAllMessages(messages: List<MessageCacheModel>)

    @Query("SELECT * FROM user_messages")
    fun getAllMessages(): List<MessageCacheModel>
}