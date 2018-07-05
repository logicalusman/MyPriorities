package com.mypriorities.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Context) {

    @Singleton
    @Provides
    fun providesApplication(): Context = application

}