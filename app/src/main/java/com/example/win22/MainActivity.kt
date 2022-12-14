package com.example.win22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.win22.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.onesignal.OneSignal

class MainActivity : AppCompatActivity() {
    private val APP_ID = "714b9f14-381d-4fc4-a93c-28d480557381"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(APP_ID)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_main_activity)
        GetBackgroundImage.setImage(constraintLayout, this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2){tab, position->
            when(position){
                0->{
                    tab.text = "Home"
                }
                1->{
                    tab.text = "Statistics"
                }
                2->{
                    tab.text = "Account"
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}