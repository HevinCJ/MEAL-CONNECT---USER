package com.example.mealconnectuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mealconnectuser.Settings
import com.example.mealconnectuser.fragments.Home
import com.example.mealconnectuser.fragments.Cart

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(
        Home(), Cart(),Settings()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}