package com.example.challengech5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.challengech5.listener.DataListener
import com.example.challengech5.model.Player

class PickerFragment(var player: Player) : Fragment() {

    private lateinit var ivRock: ImageView
    private lateinit var ivPaper: ImageView
    private lateinit var ivScissor: ImageView
    private var isFinished: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvPlayer)?.text = player.name

        ivRock = view.findViewById<ImageView>(R.id.ivRock)
        ivPaper = view.findViewById<ImageView>(R.id.ivPaper)
        ivScissor = view.findViewById<ImageView>(R.id.ivScissor)

        val mapPicked = mutableMapOf<String, ImageView>(
            "batu" to ivRock,
            "kertas" to ivPaper,
            "gunting" to ivScissor
        )

        if (player.name == "CPU" && player.playerNo == 2) {
            ivRock.isClickable = false
            ivPaper.isClickable = false
            ivScissor.isClickable = false
            player.picked = mapPicked.keys.random()
            animateAndSet(player.picked, mapPicked)
        } else {
            mapPicked.forEach()
            { (i, v) ->
                run {
                    v.setOnClickListener() {
                        ivRock.isClickable = false
                        ivPaper.isClickable = false
                        ivScissor.isClickable = false
                        player.picked = i
                        animateAndSet(i, mapPicked)
                    }
                }
            }
        }
    }

    fun setFinishedState(value: Boolean) {
        isFinished = value
    }

    private fun animateAndSet(picked: String, mapPicked: MutableMap<String, ImageView>) {
        val handler = Handler(Looper.getMainLooper())
        val isSecondPlayer: Boolean = (this.context as DataListener).setOnDataReady(player)
        if (!isSecondPlayer) {
            for (i in 0..30 step 3) {
                var j = 1
                mapPicked.forEach { (_, u) ->
                    handler.postDelayed(Runnable {
                        u.isPressed = true
                    }, ((i + j) * 500).toLong())

                    handler.postDelayed(Runnable {
                        u.isPressed = false
                        if (isFinished || i == 30) {
                            mapPicked.getValue(picked).isPressed = true
                            handler.removeCallbacksAndMessages(null)
                        }
                    }, (((i + j) * 500) + 500).toLong())

                    if (j == 3) j = 1
                    else j++
                }
            }
        } else {
            handler.postDelayed(Runnable {
                mapPicked.getValue(picked).isPressed = true
            }, 500)
        }
    }
}