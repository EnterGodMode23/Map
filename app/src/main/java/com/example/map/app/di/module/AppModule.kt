package com.example.map.app.di.module

import android.content.Context
import androidx.room.Room
import com.example.map.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "points_db"
        ).build()

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.getDao()
}