package com.tek4tv.login.di

import android.content.Context
import androidx.room.Room
import com.tek4tv.login.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

    @Provides
    @Singleton
    fun provideUserDao(appDb : AppDatabase) = appDb.userDao

    @Provides
    @Singleton
    fun provideSiteMapIdDao(appDb : AppDatabase) = appDb.siteMapIdDao

    @Provides
    @Singleton
    fun provideRoleDao(appDb : AppDatabase) = appDb.roleDao
}