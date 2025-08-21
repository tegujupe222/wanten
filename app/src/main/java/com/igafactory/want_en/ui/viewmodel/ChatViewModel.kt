package com.igafactory.want_en.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igafactory.want_en.data.model.ChatMessage
import com.igafactory.want_en.data.model.UserPersona
import com.igafactory.want_en.data.repository.ChatRepository
import com.igafactory.want_en.data.repository.ChatException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _selectedPersona = MutableStateFlow<UserPersona?>(null)
    val selectedPersona: StateFlow<UserPersona?> = _selectedPersona.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private var currentPersonaId: String? = null
    
    fun loadMessages(personaId: String) {
        currentPersonaId = personaId
        viewModelScope.launch {
            try {
                chatRepository.getMessagesByPersona(personaId).collect { messages ->
                    _messages.value = messages
                }
            } catch (e: Exception) {
                _error.value = "Failed to load messages: ${e.message}"
            }
        }
    }
    
    fun loadPersona(personaId: String) {
        viewModelScope.launch {
            try {
                val persona = chatRepository.getPersonaById(personaId)
                _selectedPersona.value = persona
                if (persona != null) {
                    loadMessages(personaId)
                }
            } catch (e: Exception) {
                _error.value = "Failed to load persona: ${e.message}"
            }
        }
    }
    
    fun sendMessage(content: String) {
        if (content.isBlank() || _selectedPersona.value == null) return
        
        val persona = _selectedPersona.value!!
        val userMessage = ChatMessage(
            content = content,
            isFromUser = true,
            personaId = persona.id
        )
        
        viewModelScope.launch {
            try {
                // Add user message immediately
                chatRepository.insertMessage(userMessage)
                
                // Generate AI response
                _isTyping.value = true
                
                val conversationHistory = _messages.value
                val aiResponse = chatRepository.generateAIResponse(
                    persona = persona,
                    conversationHistory = conversationHistory,
                    userMessage = content
                )
                
                val aiMessage = ChatMessage(
                    content = aiResponse,
                    isFromUser = false,
                    personaId = persona.id
                )
                
                chatRepository.insertMessage(aiMessage)
                _isTyping.value = false
                
            } catch (e: ChatException) {
                _error.value = e.message
                _isTyping.value = false
            } catch (e: Exception) {
                _error.value = "Failed to send message: ${e.message}"
                _isTyping.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun initializeWithDefaultPersona() {
        viewModelScope.launch {
            try {
                val persona = chatRepository.getFirstPersona()
                if (persona == null) {
                    // Create default persona if none exists
                    val defaultPersona = UserPersona.defaultPersona()
                    chatRepository.insertPersona(defaultPersona)
                    _selectedPersona.value = defaultPersona
                    loadMessages(defaultPersona.id)
                } else {
                    _selectedPersona.value = persona
                    loadMessages(persona.id)
                }
            } catch (e: Exception) {
                _error.value = "Failed to initialize: ${e.message}"
            }
        }
    }
}
