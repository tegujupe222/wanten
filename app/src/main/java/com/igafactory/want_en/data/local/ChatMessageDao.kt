package com.igafactory.want_en.data.local

import androidx.room.*
import com.igafactory.want_en.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    
    @Query("SELECT * FROM chat_messages WHERE personaId = :personaId ORDER BY timestamp ASC")
    fun getMessagesByPersona(personaId: String): Flow<List<ChatMessage>>
    
    @Query("SELECT * FROM chat_messages WHERE personaId = :personaId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentMessagesByPersona(personaId: String, limit: Int = 50): List<ChatMessage>
    
    @Query("SELECT * FROM chat_messages WHERE id = :messageId")
    suspend fun getMessageById(messageId: String): ChatMessage?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<ChatMessage>)
    
    @Update
    suspend fun updateMessage(message: ChatMessage)
    
    @Delete
    suspend fun deleteMessage(message: ChatMessage)
    
    @Query("DELETE FROM chat_messages WHERE personaId = :personaId")
    suspend fun deleteMessagesByPersona(personaId: String)
    
    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: String)
    
    @Query("SELECT COUNT(*) FROM chat_messages WHERE personaId = :personaId")
    suspend fun getMessageCountByPersona(personaId: String): Int
    
    @Query("SELECT * FROM chat_messages WHERE personaId = :personaId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastMessageByPersona(personaId: String): ChatMessage?
    
    @Query("SELECT * FROM chat_messages WHERE personaId = :personaId AND isFromUser = 0 ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastAIMessageByPersona(personaId: String): ChatMessage?
}
