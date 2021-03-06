package com.mvvm.ui

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mvvm.R
import com.mvvm.adapter.FragmentAdapter
import com.mvvm.annotation.Base
import com.mvvm.viewmodel.BaseViewModel

/**
 * @author Yang Shihao
 */
@Base
abstract class BaseViewPagerActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB, VM>() {

    @CallSuper
    override fun initView() {
        val viewPager: ViewPager2 = findViewById(R.id.baseViewPager)
        val tabLayout: TabLayout = findViewById(R.id.baseTabLayout)
        val titles = getTitles()
        val fragments = getFragments()
        viewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle, fragments)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position in titles.indices) {
                tab.text = titles[position]
            }
        }.attach()
    }

    override fun initData() {

    }

    abstract fun getTitles(): List<String>

    abstract fun getFragments(): List<FragmentCreator>
}