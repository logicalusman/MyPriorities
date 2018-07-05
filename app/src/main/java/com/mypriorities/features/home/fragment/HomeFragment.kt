package com.mypriorities.features.home.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mypriorities.MyPriorityApp
import com.mypriorities.R
import com.mypriorities.domain.MyPriority
import com.mypriorities.features.home.adapters.HomeAdapter
import com.mypriorities.features.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var homeAdapter: HomeAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel

    init {
        MyPriorityApp.dataComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(activity,viewModelFactory).get(HomeViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        setupRecyclerView()
        subscribeForViewStateChange()
        subscribeForMyPriorityList()
        subscribeForNewPriority()
        subscribeForDeletingPriority()
        lifecycle.addObserver(viewModel)
    }

    private fun subscribeForMyPriorityList() {
        viewModel.getAllPriorities()?.observe(this, Observer<List<MyPriority>> { it ->
            setAdapterItems(it)
        })
    }

    private fun subscribeForViewStateChange() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(viewState: HomeViewModel.ViewState?) {
        when (viewState) {
            is HomeViewModel.ViewState.UpdateScreenTitle -> activity.setTitle(viewState.screenTitle)
            is HomeViewModel.ViewState.ShowLoading -> showLoading(viewState.show)
            is HomeViewModel.ViewState.ShowErrorMessage -> showErrorMessage(viewState.title, viewState.message)
        }
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

    private fun subscribeForDeletingPriority() {
        homeAdapter?.priorityDeleteClickedSubject?.subscribe({
            viewModel.deletePriority(it.id!!)

        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        homeRecyclerView.layoutManager = layoutManager
        homeRecyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        homeAdapter = HomeAdapter(activity)
        homeRecyclerView.adapter = homeAdapter
    }

    private fun setAdapterItems(items: List<MyPriority>?) {
        homeAdapter?.listItems = items
    }

    private fun subscribeForNewPriority() {
        fab.setOnClickListener {
            startActivity(viewModel.intentForAddNewPriorityActivity())
        }
    }

    companion object {
        fun getHomeFragment(): Fragment {
            return HomeFragment()
        }

    }
}