package com.igafactory.want_en.data.local

import androidx.room.*
import com.igafactory.want_en.data.model.UserPersona
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    
    @Query("SELECT * FROM user_personas ORDER BY name ASC")
    fun getAllPersonas(): Flow<List<UserPersona>>
    
    @Query("SELECT * FROM user_personas WHERE id = :personaId")
    suspend fun getPersonaById(personaId: String): UserPersona?
    
    @Query("SELECT * FROM user_personas LIMIT 1")
    suspend fun getFirstPersona(): UserPersona?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersona(persona: UserPersona)
    
    @Update
    suspend fun updatePersona(persona: UserPersona)
    
    @Delete
    suspend fun deletePersona(persona: UserPersona)
    
    @Query("DELETE FROM user_personas WHERE id = :personaId")
    suspend fun deletePersonaById(personaId: String)
    
    @Query("SELECT COUNT(*) FROM user_personas")
    suspend fun getPersonaCount(): Int
    
    @Query("SELECT * FROM user_personas WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchPersonas(searchQuery: String): Flow<List<UserPersona>>
}
