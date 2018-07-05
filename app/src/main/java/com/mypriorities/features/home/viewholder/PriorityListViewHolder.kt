package com.mypriorities.features.home.viewholder

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.mypriorities.domain.MyPriority
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_list.view.*

class PriorityListViewHolder(view: View, priorityDeleteClickedSubject: PublishSubject<MyPriority>) : RecyclerView.ViewHolder(view) {

    private val itemDesc = view.item_desc
    private val itemDate = view.item_date
    private val itemTime = view.item_time_stamp
    private val deleteBtn = view.delete_priority_iv
    private var myPriority: MyPriority? = null

    init {
        deleteBtn.setOnClickListener({
            priorityDeleteClickedSubject.onNext(myPriority)
        })
    }

    fun setData(myPriorityEntity: MyPriority?) {
        this.myPriority = myPriorityEntity
        if (!TextUtils.isEmpty(myPriorityEntity?.description)) {
            itemDesc?.text = myPriorityEntity?.description
            itemDesc?.visibility = View.VISIBLE
        } else {
            itemDesc?.visibility = View.GONE
        }
        itemDate?.text = myPriorityEntity?.date
        itemTime?.text = myPriorityEntity?.time
    }
}

