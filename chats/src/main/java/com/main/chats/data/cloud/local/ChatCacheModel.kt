package com.main.chats.data.cloud.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_chats_table")
data class ChatCacheModel(
    @PrimaryKey val id: String,
    @ColumnInfo("uid") val uid: String
)