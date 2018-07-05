package com.mypriorities.features.home.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mypriorities.R
import com.mypriorities.domain.MyPriority
import com.mypriorities.features.home.viewholder.PriorityListViewHolder
import io.reactivex.subjects.PublishSubject


class HomeAdapter(val context: Context) : RecyclerView.Adapter<PriorityListViewHolder>() {

    var listItems: List<MyPriority>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }

    val priorityDeleteClickedSubject: PublishSubject<MyPriority> = PublishSubject.create<MyPriority>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PriorityListViewHolder {
        return PriorityListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false), priorityDeleteClickedSubject)
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: PriorityListViewHolder?, position: Int) {
        holder?.setData(listItems?.get(position))
    }
}