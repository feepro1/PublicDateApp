package com.main.chats.data.storage.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Query

@Entity
@Dao
interface ChatsDao {

    @Query("SELECT * FROM user_chats_table")
    fun getUserChats(): List<ChatCacheModel>
}