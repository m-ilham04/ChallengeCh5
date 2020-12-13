package com.example.challengech5

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment


class LandingPageFragment(private val pos: Int, val listener: (CharSequence?) -> Unit) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_landing_page, container, false)


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
                addEditText(view).addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) = listener(s)
                }
                )
            }
        }

    }

    @SuppressLint("ResourceType")
    override fun onPause() {
        super.onPause()
        view?.findViewById<EditText>(R.string.idNama)?.text?.clear()
    }

    @SuppressLint("ResourceType")
    private fun addEditText(view: View): EditText {
        val edt = EditText(context)
        edt.id = R.string.idNama
        edt.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        edt.textSize = 25f
        edt.setTextColor(R.color.black)
        edt.setBackgroundResource(R.drawable.bg_border_et)
        view.findViewById<LinearLayout>(R.id.linLayout).addView(edt)
        return edt
    }
}