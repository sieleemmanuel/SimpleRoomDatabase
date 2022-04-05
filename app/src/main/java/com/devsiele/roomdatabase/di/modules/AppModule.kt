package com.devsiele.roomdatabase.di.modules

import android.content.Context
import com.devsiele.roomdatabase.data.repo.MainRepository
import com.devsiele.roomdatabase.database.NoteDao
import com.devsiele.roomdatabase.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getDb(@ApplicationContext context: Context): NoteDatabase {
        return NoteDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao
    }

    @Singleton
    @Provides
    fun getRepository(noteDatabase: NoteDatabase): MainRepository {
        return MainRepository(noteDatabase.noteDao)
    }
}