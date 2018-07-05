package com.mypriorities.features.addNewPriority.activity

import android.os.Bundle
import com.mypriorities.R
import com.mypriorities.common.base.BaseActivity
import com.mypriorities.features.addNewPriority.fragment.AddNewPriorityFragment
import kotlinx.android.synthetic.main.activity_add_new_priority.*

class AddNewPriorityActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_priority)
        setupToolbar(showBackButton = true)
        val fragment = AddNewPriorityFragment.newInstance()
        supportFragmentManager.beginTransaction().add(container.id, fragment).commit()
    }
}
