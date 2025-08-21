package com.igafactory.want_en.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_personas")
data class UserPersona(
    @PrimaryKey
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val relationship: String,
    val personality: List<String>,
    val speechStyle: String,
    val catchphrases: List<String>,
    val favoriteTopics: List<String>,
    val mood: PersonaMood = PersonaMood.NEUTRAL,
    val customization: PersonaCustomization = PersonaCustomization()
) {
    val displayName: String
        get() = if (name.isEmpty()) "Unnamed" else name
    
    val moodEmoji: String
        get() = mood.emoji
    
    val personalityText: String
        get() = personality.joinToString(" • ")
    
    val catchphraseText: String
        get() = catchphrases.joinToString(" / ")
    
    val topicsText: String
        get() = favoriteTopics.joinToString(" • ")
    
    companion object {
        fun defaultPersona(): UserPersona {
            return UserPersona(
                name = "Assistant",
                relationship = "Supporter",
                personality = listOf("Friendly", "Reliable", "Kind"),
                speechStyle = "Polite and friendly tone",
                catchphrases = listOf("Hello!", "How can I help you?"),
                favoriteTopics = listOf("Daily conversation", "Advice", "Casual chat"),
                mood = PersonaMood.HAPPY,
                customization = PersonaCustomization.safeDefault()
            )
        }
    }
}

enum class PersonaMood(val displayName: String, val emoji: String) {
    HAPPY("Happy", "😊"),
    SAD("Sad", "😢"),
    EXCITED("Excited", "🤩"),
    CALM("Calm", "😌"),
    ANXIOUS("Anxious", "😰"),
    ANGRY("Angry", "😠"),
    NEUTRAL("Neutral", "😐")
}

data class PersonaCustomization(
    val avatarUrl: String = "",
    val backgroundColor: String = "#FFFFFF",
    val textColor: String = "#000000",
    val fontSize: Int = 16
) {
    companion object {
        fun safeDefault(): PersonaCustomization {
            return PersonaCustomization()
        }
    }
}
