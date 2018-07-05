package com.mypriorities.di

import android.arch.persistence.room.Room
import android.content.Context
import com.mypriorities.common.IntentFactory
import com.mypriorities.data.MyPriorityRepository
import com.mypriorities.data.db.DBRepository
import com.mypriorities.data.db.MyPriorityDatabase
import com.mypriorities.data.network.MyPrioritiesApi
import com.mypriorities.data.network.MyPrioritiesNetworkAdapter
import com.mypriorities.data.network.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun providesDatabase(application: Context): MyPriorityDatabase {
        return Room.databaseBuilder(application, MyPriorityDatabase::class.java, "mypriority_db").build()
    }

    @Provides
    @Singleton
    fun providesDBRepository(myPriorityDatabase: MyPriorityDatabase): DBRepository {
        return DBRepository(myPriorityDatabase)
    }

    @Provides
    @Singleton
    fun providesNetworkRepository(myPrioritiesApi: MyPrioritiesApi): NetworkRepository {
        return NetworkRepository(myPrioritiesApi)
    }

    @Provides
    @Singleton
    fun providesMyPrioritiesApi(): MyPrioritiesApi {
        return MyPrioritiesNetworkAdapter.myPrioritiesApi()
    }

    @Provides
    @Singleton
    fun providesMyPriorityRepository(networkRepository: NetworkRepository, dbRepository: DBRepository): MyPriorityRepository {
        return MyPriorityRepository(networkRepository, dbRepository)
    }

    @Provides
    @Singleton
    fun providesIntentFactory(application: Context) : IntentFactory{
        return IntentFactory(application)
    }
}