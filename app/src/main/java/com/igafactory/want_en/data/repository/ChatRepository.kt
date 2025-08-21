package com.igafactory.want_en.data.repository

import com.igafactory.want_en.data.local.ChatMessageDao
import com.igafactory.want_en.data.local.PersonaDao
import com.igafactory.want_en.data.model.ChatMessage
import com.igafactory.want_en.data.model.UserPersona
import com.igafactory.want_en.data.remote.ApiService
import com.igafactory.want_en.data.remote.ChatRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val personaDao: PersonaDao,
    private val chatMessageDao: ChatMessageDao,
    private val apiService: ApiService
) {
    
    // Persona operations
    fun getAllPersonas(): Flow<List<UserPersona>> = personaDao.getAllPersonas()
    
    suspend fun getPersonaById(personaId: String): UserPersona? = 
        personaDao.getPersonaById(personaId)
    
    suspend fun getFirstPersona(): UserPersona? = personaDao.getFirstPersona()
    
    suspend fun insertPersona(persona: UserPersona) = personaDao.insertPersona(persona)
    
    suspend fun updatePersona(persona: UserPersona) = personaDao.updatePersona(persona)
    
    suspend fun deletePersona(persona: UserPersona) = personaDao.deletePersona(persona)
    
    suspend fun getPersonaCount(): Int = personaDao.getPersonaCount()
    
    fun searchPersonas(query: String): Flow<List<UserPersona>> = 
        personaDao.searchPersonas(query)
    
    // Chat message operations
    fun getMessagesByPersona(personaId: String): Flow<List<ChatMessage>> = 
        chatMessageDao.getMessagesByPersona(personaId)
    
    suspend fun getRecentMessagesByPersona(personaId: String, limit: Int = 50): List<ChatMessage> = 
        chatMessageDao.getRecentMessagesByPersona(personaId, limit)
    
    suspend fun insertMessage(message: ChatMessage) = chatMessageDao.insertMessage(message)
    
    suspend fun insertMessages(messages: List<ChatMessage>) = chatMessageDao.insertMessages(messages)
    
    suspend fun deleteMessagesByPersona(personaId: String) = 
        chatMessageDao.deleteMessagesByPersona(personaId)
    
    suspend fun getLastMessageByPersona(personaId: String): ChatMessage? = 
        chatMessageDao.getLastMessageByPersona(personaId)
    
    // AI API operations
    suspend fun generateAIResponse(
        persona: UserPersona,
        conversationHistory: List<ChatMessage>,
        userMessage: String,
        emotionContext: String? = null
    ): String {
        val request = ChatRequest(
            persona = persona,
            conversationHistory = conversationHistory,
            userMessage = userMessage,
            emotionContext = emotionContext
        )
        
        return try {
            val response = apiService.generateResponse(request)
            response.response
        } catch (e: Exception) {
            throw ChatException("Failed to generate AI response: ${e.message}")
        }
    }
}

class ChatException(message: String) : Exception(message)
