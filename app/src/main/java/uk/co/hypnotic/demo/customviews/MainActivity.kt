package uk.co.hypnotic.demo.customviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme((application as ClockApplication).currentTheme)
        setContentView(R.layout.activity_main)


        val pager = findViewById<ViewPager2>(R.id.pager)

        val pagerAdapter = MainFragmentStateAdapter(this)
        pager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = pagerAdapter.getTabName(position)
        }.attach()

    }

    private inner class MainFragmentStateAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
                    ClockFragment()
                }
                1 -> {
                    ComplexClockFragment()
                }
                2 -> {
                    ClockFragment()
                }
                else -> {
                    throw IllegalStateException("Unknown tab position")
                }
            }

        }

        fun getTabName(position : Int) : String {
            return when(position) {
                0 -> {
                    resources.getString(R.string.clock)
                }
                1 -> {
                    resources.getString(R.string.complex_clock)
                }
                2 -> {
                    resources.getString(R.string.foreign_fragment)
                }
                else -> {
                    throw IllegalStateException("Unknown tab position")
                }
            }
        }
    }

    fun changeTheme(view : View) {
        when(view.id) {
            R.id.brasil -> (application as ClockApplication).currentTheme = R.style.Theme_CustomViews_Brasil
            R.id.france -> (application as ClockApplication).currentTheme = R.style.Theme_CustomViews_France
            R.id.italy -> (application as ClockApplication).currentTheme = R.style.Theme_CustomViews_Italy
            else -> {
                throw IllegalStateException("Unknown button")
            }
        }

        recreate()
    }

}
