package com.kashapovrush.di

import android.app.Application
import com.kashapovrush.data.AppDatabase
import com.kashapovrush.data.PartListDao
import com.kashapovrush.data.PartRepositoryImpl
import com.kashapovrush.domain.PartRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: PartRepositoryImpl): PartRepository

    companion object {

        @ApplicationScope
        @Provides
        fun providePartListDao(application: Application): PartListDao {
            return AppDatabase.getInstance(application).partListDao()
        }
    }
}