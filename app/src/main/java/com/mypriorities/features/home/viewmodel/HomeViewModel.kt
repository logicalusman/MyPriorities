package com.mypriorities.features.home.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.annotation.StringRes
import com.mypriorities.MyPriorityApp
import com.mypriorities.R
import com.mypriorities.common.IntentFactory
import com.mypriorities.data.MyPriorityRepository
import com.mypriorities.domain.ErrorType
import com.mypriorities.domain.MyPriority
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val myPriorityRepository: MyPriorityRepository, private val intentFactory: IntentFactory) : ViewModel(), LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show: Boolean) : ViewState()
        class ShowErrorMessage(@StringRes val title: Int, @StringRes val message: Int) : ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle: Int) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        viewState.value = ViewState.UpdateScreenTitle(R.string.app_name)
    }

    fun getAllPriorities(): LiveData<List<MyPriority>>? {
        return myPriorityRepository.getAllPriorities()
    }

    fun intentForAddNewPriorityActivity(): Intent {
        return intentFactory.intentForAddNewPriorityActivity()
    }

    fun deletePriority(priorityId: Long) {
        viewState.value = ViewState.ShowLoading(true)
        subscriptions.add(
                myPriorityRepository.deletePriority(priorityId).subscribe {
                    viewState.value = ViewState.ShowLoading(false)
                    if (!it.success) {
                        when (it.errorType) {
                            is ErrorType.NetworkError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.network_error)
                            is ErrorType.TimeoutError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.connection_timeout)
                            else -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.unknown_error)
                        }
                    }
                }
        )
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }


}
