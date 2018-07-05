package com.mypriorities.common.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

/**
 * Created by Usman on 24/03/2018.
 */
open class BaseActivity : AppCompatActivity() {

    // Children of this activity can set it to true in order to override the back key (arrow) behavior.
    // Default behavior finishes the activity when back key is pressed
    var overrideBackButtonBehavior: Boolean = false

    fun setupToolbar(title: String? = null, showBackButton: Boolean) {
        title?.let {
            supportActionBar?.setTitle(title)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (!overrideBackButtonBehavior && item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}