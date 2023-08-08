package com.example.vyaperclone

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDataBase(
        app: Application
    ): VyaparDatabase {
        return Room.databaseBuilder(app, VyaparDatabase::class.java, "VyaparDatabase")
            .fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideItemRepository(
        database: VyaparDatabase
    ): ItemsRepository {
        return ItemsRepository(database.getDAO())
    }
}