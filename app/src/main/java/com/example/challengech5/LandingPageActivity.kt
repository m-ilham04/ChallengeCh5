package com.example.challengech5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.challengech5.model.Player
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlin.system.exitProcess

class LandingPagesActivity : AppCompatActivity() {

    private lateinit var left: ImageView
    private lateinit var right: ImageView
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: SpringDotsIndicator
    private lateinit var landingPageCallback: OnPageChangeCallback
    private lateinit var player: Player
    private val landingPageNum = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_pages)

        left = this.findViewById(R.id.ivLeft)
        right = this.findViewById(R.id.ivRight)

        viewPager = findViewById(R.id.pager)
        landingPageCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setNavigationPanel()
            }
        }
        viewPager.registerOnPageChangeCallback(landingPageCallback)

        val pagerAdapter = LandingPageAdapter(this, landingPageNum) {
            if (it.toString() != "") {
                setNavigationPanel(it.toString())
            }
        }
        viewPager.adapter = pagerAdapter

        dotsIndicator = findViewById(R.id.spring_dots_indicator)
        dotsIndicator.setViewPager2(viewPager)

    }

    override fun onPause() {
        super.onPause()
        right.setTint(this, R.color.blue_bt_off)
        right.isClickable = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(landingPageCallback)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            finishAffinity()
            exitProcess(0)
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun setNavigationPanel(playerName: String = "") {

        when (viewPager.currentItem) {
            0 -> {
                left.setTint(this, R.color.blue_bt_off)
                right.setTint(this, R.color.blue_bt_on)

                left.isClickable = false
                right.setOnClickListener {
                    viewPager.currentItem += 1
                }
            }

            (landingPageNum - 1) -> {
                left.setTint(this, R.color.blue_bt_on)
                right.setTint(this, R.color.blue_bt_off)

                left.isClickable = true
                left.setOnClickListener {
                    viewPager.currentItem -= 1
                }

                if (playerName != "") {
                    right.setTint(this, R.color.blue_bt_on)
                    right.isClickable = true
                    right.setOnClickListener {
                        player =
                            Player(playerName, 1)
                        val intent = Intent(this, MenuActivity::class.java)
                        intent.putExtra("Player", player)
                        finish()
                        startActivity(intent)
                    }
                }
            }
            else -> {
                left.setTint(this, R.color.blue_bt_on)
                right.setTint(this, R.color.blue_bt_on)

                left.isClickable = true
                right.setOnClickListener {
                    viewPager.currentItem += 1
                }
                left.setOnClickListener {
                    viewPager.currentItem -= 1
                }
            }
        }
    }

    private inner class LandingPageAdapter(
        fa: FragmentActivity,
        val page: Int,
        val listener: (CharSequence?) -> Unit
    ) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = page
        override fun createFragment(position: Int): Fragment =
            LandingPageFragment(position, listener)
    }
}

fun ImageView.setTint(context: Context, color: Int) {
    drawable.mutate()
    drawable.setTint(getColor(context, color))
}