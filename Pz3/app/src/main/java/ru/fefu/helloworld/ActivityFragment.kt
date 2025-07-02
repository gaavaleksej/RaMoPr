package ru.fefu.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ActivityFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_activity, container, false).apply {
            viewPager = findViewById(R.id.view_pager)
            tabLayout = findViewById(R.id.tab_layout)
            viewPager.adapter = ViewPagerAdapter(requireActivity())

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Мои"
                    1 -> "Пользователей"
                    else -> ""
                }
            }.attach()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ActivityFragment()
    }
}
