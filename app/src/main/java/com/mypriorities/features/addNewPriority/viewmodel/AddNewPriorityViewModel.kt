package com.mypriorities.features.addNewPriority.viewmodel

import android.arch.lifecycle.*
import android.support.annotation.StringRes
import android.text.TextUtils
import com.mypriorities.R
import com.mypriorities.data.MyPriorityRepository
import com.mypriorities.domain.ErrorType
import com.mypriorities.domain.MyPriority
import io.reactivex.disposables.CompositeDisposable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Usman on 25/03/2018.
 */

class AddNewPriorityViewModel @Inject constructor(private val myPriorityRepository: MyPriorityRepository) : ViewModel(), LifecycleObserver {

    sealed class ViewState {
        class ShowLoading(val show: Boolean) : ViewState()
        class ShowErrorMessage(@StringRes val title: Int, @StringRes val message: Int) : ViewState()
        class OnPriorityAdded() : ViewState()
        class DescriptionError(@StringRes val message: Int) : ViewState()
        class DateError(@StringRes val message: Int) : ViewState()
        class TimeError(@StringRes val message: Int) : ViewState()
        class ResetErrors() : ViewState()
        class UpdateScreenTitle(@StringRes val screenTitle : Int) : ViewState()
    }

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val subscriptions = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated(){
        viewState.value = ViewState.UpdateScreenTitle(R.string.new_priority)
    }

    fun formatDate(day: Int, month: Int, year: Int) = String.format("%02d-%02d-%s", day, month + 1, year)

    fun formatTime(hour: Int, minute: Int) = String.format("%02d:%02d", hour, minute)

    fun addNewPriority(description: String?, date: String?, time: String?) {
        viewState.value = ViewState.ResetErrors()
        if (!validateInputFields(description, date, time)) {
            return
        }
        viewState.value = ViewState.ShowLoading(true)
        val myPriority = MyPriority(description = description!!, date = date!!, time = time!!)
        subscriptions.add(
                myPriorityRepository.addNewPriority(myPriority).subscribe(
                        {
                            viewState.value = ViewState.ShowLoading(false)
                            if (it.success) {
                                viewState.value = ViewState.OnPriorityAdded()
                            } else {
                                when (it.errorType) {
                                    is ErrorType.NetworkError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.network_error)
                                    is ErrorType.TimeoutError -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.connection_timeout)
                                    else -> viewState.value = ViewState.ShowErrorMessage(R.string.error, R.string.unknown_error)
                                }
                            }
                        }
                )
        )
    }

    private fun validateInputFields(description: String?, date: String?, time: String?): Boolean {
        return isValidDescription(description) and isValidDate(date) and isValidTime(time) and isValidDateTime(date, time)
    }

    private fun isValidDateTime(date: String?, time: String?): Boolean {
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
            return false
        }
        val dateTime = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(String.format("%s %s", date, time))
        if (dateTime.before(Calendar.getInstance().time)) {
            viewState.value = ViewState.DateError(R.string.priority_date_time_today_or_future)
            return false
        }
        return true
    }

    private fun isValidDate(date: String?): Boolean {
        val isNull = isn@{ d: String? ->
            if (d == null) {
                viewState.value = ViewState.DateError(R.string.invalid_date)
                return@isn true
            }
            return@isn false
        }
        val isValidDate = vd@{ d: String ->
            try {
                SimpleDateFormat("dd-MM-yyyy").parse(d)
            } catch (e: ParseException) {
                viewState.value = ViewState.DateError(R.string.invalid_date)
                return@vd false
            }
            return@vd true
        }
        return !isNull(date) && isValidDate(date!!)
    }

    private fun isValidTime(time: String?): Boolean {
        val isNull = isn@{ t: String? ->
            if (t == null) {
                viewState.value = ViewState.TimeError(R.string.invalid_time)
                return@isn true
            }
            return@isn false
        }
        val isValidTime = ivt@{ t: String ->
            try {
                SimpleDateFormat("HH:mm").parse(t)
            } catch (e: ParseException) {
                viewState.value = ViewState.TimeError(R.string.invalid_time)
                return@ivt false
            }
            return@ivt true
        }
        return !isNull(time) && isValidTime(time!!)
    }

    private fun isValidDescription(description: String?): Boolean {
        if (description?.trim()?.length!! >= 3) {
            return true
        }
        viewState.value = ViewState.DescriptionError(R.string.priority_description_error_msg)
        return false
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }
}