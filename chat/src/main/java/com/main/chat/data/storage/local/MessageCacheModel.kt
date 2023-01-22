package com.main.chat.data.storage.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user_messages")
data class MessageCacheModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("message") val message: String,
    @ColumnInfo("senderUid") val senderUid: String,
    @ColumnInfo("receiverUid") val receiverUid: String,
    @ColumnInfo("dateTimeMillis") val dateTimeMillis: Long = 0,
    @ColumnInfo("isReported") val isReported: Boolean = false
)