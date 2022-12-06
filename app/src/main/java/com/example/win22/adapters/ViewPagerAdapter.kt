package com.example.win22.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.win22.fragments.FragmentAccount
import com.example.win22.fragments.FragmentMain
import com.example.win22.fragments.FragmentStat

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                FragmentMain()
            }
            1->{
                FragmentStat()
            }
            2->{
                FragmentAccount()
            }
            else->{
                Fragment()
            }
        }
    }
}