package com.mypriorities.features.addNewPriority.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.jakewharton.rxbinding.view.RxView
import com.mypriorities.MyPriorityApp
import com.mypriorities.R
import com.mypriorities.features.addNewPriority.viewmodel.AddNewPriorityViewModel
import kotlinx.android.synthetic.main.fragment_add_new_priority.*
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class AddNewPriorityFragment : Fragment() {

    private val TAG: String = "AddNewPriorityFragment"
    private lateinit var viewModel: AddNewPriorityViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var datePickerDialog: AlertDialog? = null
    private var timePickerDialog: AlertDialog? = null
    private val subscriptions = CompositeSubscription()

    init {
        MyPriorityApp.dataComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater?.inflate(R.layout.fragment_add_new_priority, container, false)!!
        viewModel = ViewModelProviders.of(activity,viewModelFactory).get(AddNewPriorityViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        setupViews()
        subscribeForViewStateChange()
        lifecycle.addObserver(viewModel)
    }

    private fun subscribeForViewStateChange() {
        viewModel.viewState.observe(this, Observer { viewState -> updateViewState(viewState) })
    }

    private fun updateViewState(viewState: AddNewPriorityViewModel.ViewState?) {
        when (viewState) {
            is AddNewPriorityViewModel.ViewState.UpdateScreenTitle -> activity.setTitle(viewState.screenTitle)
            is AddNewPriorityViewModel.ViewState.ShowLoading -> showLoading(viewState.show)
            is AddNewPriorityViewModel.ViewState.ShowErrorMessage -> showErrorMessage(viewState.title, viewState.message)
            is AddNewPriorityViewModel.ViewState.OnPriorityAdded -> onPriorityAdded()
            is AddNewPriorityViewModel.ViewState.ResetErrors -> resetErrorState()
            is AddNewPriorityViewModel.ViewState.DescriptionError -> showDescriptionError(viewState.message)
            is AddNewPriorityViewModel.ViewState.DateError -> showDateError(viewState.message)
            is AddNewPriorityViewModel.ViewState.TimeError -> showTimeError(viewState.message)
        }
    }

    private fun showDescriptionError(@StringRes message: Int) {
        description_et.error = getString(message)
    }

    private fun showDateError(@StringRes message: Int) {
        date_et.error = getString(message)
    }

    private fun showTimeError(@StringRes message: Int) {
        time_et.error = getString(message)
    }

    private fun onPriorityAdded() {
        activity.finish()
    }

    private fun showErrorMessage(@StringRes title: Int, @StringRes message: Int) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    private fun setupViews() {
        subscriptions.add(
                RxView.clicks(date_et).subscribe({
                    showDatePicker()
                })
        )

        subscriptions.add(
                RxView.clicks(time_et).subscribe({
                    showTimePicker()
                })
        )

        subscriptions.add(
                RxView.clicks(create_btn).subscribe() {
                    viewModel.addNewPriority(description_et.text.toString(), date_et.text.toString(), time_et.text.toString())
                }
        )
    }

    private fun resetErrorState() {
        description_et.error = null
        date_et.error = null
        time_et.error = null
    }

    private fun showTimePicker() {
        val timePicker = TimePicker(activity)
        timePickerDialog = AlertDialog.Builder(activity).setView(timePicker).setPositiveButton(R.string.ok, { di, i ->
            val time = viewModel.formatTime(timePicker.currentHour, timePicker.currentMinute)
            time_et.setText(time)
        }).create()
        timePickerDialog?.show()
    }

    private fun showDatePicker() {
        val datePicker = DatePicker(activity)
        datePickerDialog = AlertDialog.Builder(activity).setView(datePicker).setPositiveButton(R.string.ok,
                { di, i ->
                    val date = viewModel.formatDate(datePicker.dayOfMonth, datePicker.month, datePicker.year)
                    date_et.setText(date)
                }
        ).create()
        datePickerDialog?.show()
    }

    override fun onDestroy() {

        if (datePickerDialog?.isShowing == true) {
            datePickerDialog?.dismiss()
        }

        if (timePickerDialog?.isShowing == true) {
            timePickerDialog?.dismiss()
        }

        subscriptions.clear()
        super.onDestroy()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment
         *
         * @return A new instance of fragment AddNewPriorityFragment.
         */
        fun newInstance(): AddNewPriorityFragment {
            return AddNewPriorityFragment()
        }
    }
}
