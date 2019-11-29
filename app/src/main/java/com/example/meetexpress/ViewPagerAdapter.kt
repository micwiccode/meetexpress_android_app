package com.example.meetexpress

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val FragmentList = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
        return FragmentList[position]
    }

    override fun getCount(): Int {
        return FragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        FragmentList.add(fragment)
    }
}