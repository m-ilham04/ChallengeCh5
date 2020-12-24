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

class PickerFragmentCPU(private var player: Player) : Fragment() {

    private lateinit var ivRock: ImageView
    private lateinit var ivPaper: ImageView
    private lateinit var ivScissor: ImageView
    private lateinit var mapPicked: MutableMap<String, ImageView>
    lateinit var handler: Handler
    private var isFinished: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        view.findViewById<TextView>(R.id.tvPlayer)?.text = player.name

        ivRock = view.findViewById(R.id.ivRock)
        ivPaper = view.findViewById(R.id.ivPaper)
        ivScissor = view.findViewById(R.id.ivScissor)

        mapPicked = mutableMapOf(
            "batu" to ivRock,
            "kertas" to ivPaper,
            "gunting" to ivScissor
        )

        ivRock.isClickable = false
        ivPaper.isClickable = false
        ivScissor.isClickable = false

    }

    fun animateAndSet() {

        for (i in 0..12 step 3) {
            var j = 1
            mapPicked.forEach { (_, u) ->
                handler.postDelayed({
                    u.isPressed = true
                }, ((i + j) * 250).toLong())

                handler.postDelayed({
                    u.isPressed = false
                    if (isFinished || i == 12) {
                        player.picked = mapPicked.keys.random()
                        mapPicked.getValue(player.picked).isPressed = true
                        (this.context as DataListener).setOnDataReady(player)
                        handler.removeCallbacksAndMessages(null)
                    }
                }, (((i + j) * 250) + 250).toLong())

                if (j == 3) j = 1
                else j++
            }
        }
    }

    fun reset(){
        isFinished = false
        handler.removeCallbacksAndMessages(null)
        ivRock.isClickable = false
        ivPaper.isClickable = false
        ivScissor.isClickable = false
        ivRock.isPressed = false
        ivPaper.isPressed = false
        ivScissor.isPressed = false
    }
}
