package com.igafactory.want_en.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igafactory.want_en.data.model.UserPersona
import com.igafactory.want_en.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    
    private val _personas = MutableStateFlow<List<UserPersona>>(emptyList())
    val personas: StateFlow<List<UserPersona>> = _personas.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadPersonas()
    }
    
    fun loadPersonas() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                chatRepository.getAllPersonas().collect { personas ->
                    _personas.value = personas
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = "Failed to load personas: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    fun addPersona(persona: UserPersona) {
        viewModelScope.launch {
            try {
                chatRepository.insertPersona(persona)
            } catch (e: Exception) {
                _error.value = "Failed to add persona: ${e.message}"
            }
        }
    }
    
    fun updatePersona(persona: UserPersona) {
        viewModelScope.launch {
            try {
                chatRepository.updatePersona(persona)
            } catch (e: Exception) {
                _error.value = "Failed to update persona: ${e.message}"
            }
        }
    }
    
    fun deletePersona(persona: UserPersona) {
        viewModelScope.launch {
            try {
                chatRepository.deletePersona(persona)
            } catch (e: Exception) {
                _error.value = "Failed to delete persona: ${e.message}"
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    fun createDefaultPersona() {
        val defaultPersona = UserPersona.defaultPersona()
        addPersona(defaultPersona)
    }
    
    suspend fun getPersonaById(personaId: String): UserPersona? {
        return try {
            chatRepository.getPersonaById(personaId)
        } catch (e: Exception) {
            _error.value = "Failed to get persona: ${e.message}"
            null
        }
    }
}
