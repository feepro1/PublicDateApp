package com.main.chats.data.storage.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_chats_table")
data class ChatsCacheModel(
    @PrimaryKey val id: String,
    @ColumnInfo("uid") val uid: String
)