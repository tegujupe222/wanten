package com.igafactory.want_en.di

import android.content.Context
import com.igafactory.want_en.data.local.AppDatabase
import com.igafactory.want_en.data.local.ChatMessageDao
import com.igafactory.want_en.data.local.PersonaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    @Provides
    fun providePersonaDao(database: AppDatabase): PersonaDao {
        return database.personaDao()
    }
    
    @Provides
    fun provideChatMessageDao(database: AppDatabase): ChatMessageDao {
        return database.chatMessageDao()
    }
}
