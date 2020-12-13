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

class PickerFragment(private var player: Player) : Fragment() {

    private lateinit var ivRock: ImageView
    private lateinit var ivPaper: ImageView
    private lateinit var ivScissor: ImageView
    private lateinit var mapPicked: MutableMap<String, ImageView>
    private lateinit var handler: Handler
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

        if (player.name == "CPU" && player.playerNo == 2) {
            ivRock.isClickable = false
            ivPaper.isClickable = false
            ivScissor.isClickable = false
        } else {
            mapPicked.forEach()
            { (i, v) ->
                run {
                    v.setOnClickListener {
                        ivRock.isClickable = false
                        ivPaper.isClickable = false
                        ivScissor.isClickable = false
                        player.picked = i

                        //Kondisi jika melawan pemain 2 CPU
                        if (player.name == "CPU" && player.playerNo == 2) {

                            (this.context as DataListener).setOnDataReady(player)
                            
                            handler.postDelayed({
                                mapPicked.getValue(i).isPressed = true
                            }, 500)

                        }
                        //Kondisi jika melawan pemain 2 non CPU
                        else {
                            if (!(this.context as DataListener).setOnDataReady(player)) animateAndSet(
                                i
                            )
                            else {
                                
                                handler.postDelayed({
                                    mapPicked.getValue(i).isPressed = true
                                }, 500)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
    }

    fun setFinishedState() {
        if (player.name == "CPU" && player.playerNo == 2) {
            
            handler.postDelayed({
                isFinished = true
                (this.context as DataListener).setResultOnCPUFinished()
            }, 3000)
        } else {
            isFinished = true
        }
    }

    fun cpuStartPicking() {
        player.picked = mapPicked.keys.random()
        (this.context as DataListener).setOnDataReady(player)
        animateAndSet(player.picked)
    }

    private fun animateAndSet(picked: String) {
        
        for (i in 0..30 step 3) {
            var j = 1
            mapPicked.forEach { (_, u) ->
                handler.postDelayed({
                    u.isPressed = true
                }, ((i + j) * 500).toLong())

                handler.postDelayed({
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
    }
}
