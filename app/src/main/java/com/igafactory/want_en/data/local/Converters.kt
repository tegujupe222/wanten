package com.igafactory.want_en.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.igafactory.want_en.data.model.PersonaCustomization
import com.igafactory.want_en.data.model.PersonaMood
import java.util.Date

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromPersonaMood(mood: PersonaMood): String {
        return mood.name
    }
    
    @TypeConverter
    fun toPersonaMood(value: String): PersonaMood {
        return PersonaMood.valueOf(value)
    }
    
    @TypeConverter
    fun fromPersonaCustomization(customization: PersonaCustomization): String {
        return gson.toJson(customization)
    }
    
    @TypeConverter
    fun toPersonaCustomization(value: String): PersonaCustomization {
        return gson.fromJson(value, PersonaCustomization::class.java)
    }
}
