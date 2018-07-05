package com.mypriorities.features.home.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.mypriorities.R
import com.mypriorities.features.home.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment: Fragment = HomeFragment.getHomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.homeContainer, fragment).commit()
    }
}
