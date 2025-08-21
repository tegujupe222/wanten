package com.igafactory.want_en.data.remote

import com.igafactory.want_en.data.model.ChatMessage
import com.igafactory.want_en.data.model.UserPersona
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    
    @POST("api/gemini-proxy")
    suspend fun generateResponse(
        @Body request: ChatRequest
    ): ChatResponse
}

data class ChatRequest(
    val persona: UserPersona,
    val conversationHistory: List<ChatMessage>,
    val userMessage: String,
    val emotionContext: String? = null
)

data class ChatResponse(
    val response: String,
    val error: String? = null,
    val model: String? = null
)
