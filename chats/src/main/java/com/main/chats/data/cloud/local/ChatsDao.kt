package com.main.chats.data.cloud.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Query
import com.main.chats.data.entities.Chat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Entity
@Dao
interface ChatsDao {

    @Query("SELECT * FROM user_chats_table")
    fun getUserChats(): LiveData<List<ChatCacheModel>>
}