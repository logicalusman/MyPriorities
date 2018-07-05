package com.mypriorities.di

import com.mypriorities.data.MyPriorityRepository
import com.mypriorities.data.network.NetworkRepository
import com.mypriorities.features.addNewPriority.fragment.AddNewPriorityFragment
import com.mypriorities.features.addNewPriority.viewmodel.AddNewPriorityViewModel
import com.mypriorities.features.home.fragment.HomeFragment
import com.mypriorities.features.home.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ViewModelModule::class))
interface DataComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(addNewPriorityFragment: AddNewPriorityFragment)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(addNewPriorityViewModel: AddNewPriorityViewModel)
    fun inject(networkRepository: NetworkRepository)
    fun inject(myPriorityRepository: MyPriorityRepository)
}