package com.mypriorities.features.home.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.test.mock.MockContext
import com.mypriorities.R
import com.mypriorities.common.IntentFactory
import com.mypriorities.data.MyPriorityRepository
import com.mypriorities.domain.MyPriority
import com.mypriorities.domain.Result
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Tests the HomeVieModel.
 */
class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    val myPriority1 = MyPriority(123, "Meeting with Mario", "01-05-2018", "17:50")
    val myPriority2 = MyPriority(234, "Family Dinner", "04-06-2018", "19:00")
    val liveData: MutableLiveData<List<MyPriority>> = MutableLiveData()
    lateinit var myPriorityList: ArrayList<MyPriority>
    lateinit var myPriorityRepositoryMock: MyPriorityRepository
    lateinit var homeViewModel: HomeViewModel
    val intentFactory: IntentFactory = IntentFactory(MockContext())
    val mockObserver: Observer<HomeViewModel.ViewState> = mock()

    @Before
    fun setUp() {
        myPriorityList = ArrayList()
        myPriorityList.add(myPriority1)
        myPriorityList.add(myPriority2)
        liveData.value = myPriorityList
        myPriorityRepositoryMock = mock {
            on { getAllPriorities() } doReturn liveData
        }
        homeViewModel = HomeViewModel(myPriorityRepositoryMock, intentFactory)
        homeViewModel.viewState.observeForever(mockObserver)

    }

    @Test
    fun onCreated() {
        // As the view goes to onCreate state, the viewmodel sets the title to the view.
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.addObserver(homeViewModel)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        // verify if livedata observer has got the right state
        verify(mockObserver).onChanged(homeViewModel.viewState.value as HomeViewModel.ViewState.UpdateScreenTitle)
        // verify if UpdateScreenTitle state has the right screen title
        Assert.assertEquals(R.string.app_name, (homeViewModel.viewState.value as HomeViewModel.ViewState.UpdateScreenTitle).screenTitle)
    }

    @Test
    fun getAllPriorities() {
        Assert.assertEquals(homeViewModel.getAllPriorities(), liveData)
    }

    @Test
    fun deletePriority() {
        val deleteResult = Result.fomData(true)
        val deleteObservable: Observable<Result<Boolean>> = Observable.just(deleteResult)
        whenever(myPriorityRepositoryMock.deletePriority(123)).thenReturn(deleteObservable)
        homeViewModel.deletePriority(123)
        verify(mockObserver).onChanged(homeViewModel.viewState.value as HomeViewModel.ViewState.ShowLoading)
        Assert.assertEquals(false, (homeViewModel.viewState.value as HomeViewModel.ViewState.ShowLoading).show)
    }
}