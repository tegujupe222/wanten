package com.igafactory.want_en.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey
    val id: String = java.util.UUID.randomUUID().toString(),
    val content: String,
    val isFromUser: Boolean,
    val timestamp: Date = Date(),
    val personaId: String? = null,
    val emotionContext: String? = null,
    val messageType: MessageType = MessageType.TEXT
) {
    val isUserMessage: Boolean
        get() = isFromUser
    
    val isAIMessage: Boolean
        get() = !isFromUser
    
    val formattedTime: String
        get() = formatTime(timestamp)
    
    private fun formatTime(date: Date): String {
        val now = Date()
        val diffInMillis = now.time - date.time
        val diffInMinutes = diffInMillis / (1000 * 60)
        val diffInHours = diffInMinutes / 60
        val diffInDays = diffInHours / 24
        
        return when {
            diffInMinutes < 1 -> "Just now"
            diffInMinutes < 60 -> "$diffInMinutes min ago"
            diffInHours < 24 -> "$diffInHours hr ago"
            diffInDays < 7 -> "$diffInDays days ago"
            else -> {
                val formatter = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                formatter.format(date)
            }
        }
    }
}

enum class MessageType {
    TEXT,
    IMAGE,
    FILE,
    SYSTEM
}

data class ChatRoom(
    val id: String = java.util.UUID.randomUUID().toString(),
    val personaId: String,
    val personaName: String,
    val lastMessage: String? = null,
    val lastMessageTime: Date? = null,
    val unreadCount: Int = 0,
    val isAIMode: Boolean = false
) {
    val displayName: String
        get() = if (personaName.isEmpty()) "Unnamed Chat" else personaName
    
    val lastMessagePreview: String
        get() = lastMessage?.take(50)?.let { 
            if (it.length == 50) "$it..." else it 
        } ?: "No messages yet"
    
    val formattedLastMessageTime: String
        get() = lastMessageTime?.let { 
            val now = Date()
            val diffInMillis = now.time - it.time
            val diffInMinutes = diffInMillis / (1000 * 60)
            val diffInHours = diffInMinutes / 60
            val diffInDays = diffInHours / 24
            
            when {
                diffInMinutes < 1 -> "Just now"
                diffInMinutes < 60 -> "$diffInMinutes min ago"
                diffInHours < 24 -> "$diffInHours hr ago"
                diffInDays < 7 -> "$diffInDays days ago"
                else -> {
                    val formatter = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                    formatter.format(it)
                }
            }
        } ?: ""
}
