package com.mypriorities.common

import android.content.Context
import android.content.Intent
import com.mypriorities.features.addNewPriority.activity.AddNewPriorityActivity
import javax.inject.Inject

class IntentFactory @Inject constructor(val applicationContext: Context) {
    fun intentForAddNewPriorityActivity(): Intent {
        return Intent(applicationContext, AddNewPriorityActivity::class.java)
    }
}