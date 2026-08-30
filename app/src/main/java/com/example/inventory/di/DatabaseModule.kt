package com.example.inventory.di

import android.content.Context
import androidx.room.Room
import com.example.inventory.data.local.AppDatabase
import com.example.inventory.data.local.ItemDao
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inventory_database"
        ).build()
    }

    @Provides
    fun provideItemDao(
        database: AppDatabase
    ): ItemDao {
        return database.itemDao()
    }
}