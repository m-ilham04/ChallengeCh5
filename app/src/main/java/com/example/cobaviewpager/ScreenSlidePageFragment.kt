package com.example.cobaviewpager

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet


class ScreenSlidePageFragment(private val pos: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.screen_slide_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (this.pos) {
            0 -> {
                view.findViewById<ImageView>(R.id.ivIcon)
                    .setImageResource(R.drawable.ic_landing_page1)
                view.findViewById<TextView>(R.id.tvInfo).text =
                    "Bermain suit melawan sesama pemain."
            }

            1 -> {
                view.findViewById<ImageView>(R.id.ivIcon)
                    .setImageResource(R.drawable.ic_landing_page2)
                view.findViewById<TextView>(R.id.tvInfo).text = "Bermain suit melawan komputer."
            }

            2 -> {
                view.findViewById<ImageView>(R.id.ivIcon)
                    .setImageResource(R.drawable.ic_landing_page3)
                view.findViewById<TextView>(R.id.tvInfo).text = "Tuliskan namamu di bawah."
            }
        }

    }
}
