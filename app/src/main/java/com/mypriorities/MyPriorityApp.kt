package com.mypriorities

import android.app.Application
import android.content.Context
import com.mypriorities.di.AppModule
import com.mypriorities.di.DaggerDataComponent
import com.mypriorities.di.DataComponent
import com.mypriorities.di.DataModule

class MyPriorityApp : Application() {


    companion object {
        lateinit var applicationContext: Context
        lateinit var dataComponent: DataComponent
    }

    override fun onCreate() {
        super.onCreate()
        MyPriorityApp.applicationContext = this
        dataComponent = DaggerDataComponent.builder().appModule(AppModule(this)).dataModule(DataModule()).build()

    }


}