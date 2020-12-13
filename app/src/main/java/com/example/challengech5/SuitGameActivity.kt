package com.example.challengech5

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.challengech5.listener.CallbackResult
import com.example.challengech5.listener.DataListener
import com.example.challengech5.model.Player
import com.example.challengech5.model.Suit


class SuitGameActivity : AppCompatActivity(), DataListener, CallbackResult {

    private lateinit var suit: Suit
    private var playerCount = 0
    private lateinit var ftPlayer1: FragmentTransaction
    private lateinit var ftPlayer2: FragmentTransaction
    private lateinit var pickerPlayer1: PickerFragment
    private lateinit var pickerPlayer2: PickerFragment
    private lateinit var reset: ImageView
    private lateinit var close: ImageView
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suit_game)

        suit = intent.getParcelableExtra<Suit>("Game") as Suit

        handler = Handler(Looper.getMainLooper())

        val hdIcon = findViewById<ImageView>(R.id.ivHeader)
        Glide.with(this).load(this.resources.getString(R.string.header_url)).into(hdIcon)

        ftPlayer1 = supportFragmentManager.beginTransaction()
        pickerPlayer1 = PickerFragment(suit.player1)
        ftPlayer1.add(R.id.frameP1, pickerPlayer1).commit()

        ftPlayer2 = supportFragmentManager.beginTransaction()
        pickerPlayer2 = PickerFragment(suit.player2)
        ftPlayer2.add(R.id.frameP2, pickerPlayer2).commit()

        reset = findViewById(R.id.ivReset)
        close = findViewById(R.id.ivClose)

        reset.setOnClickListener { reset() }
        close.setOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()
        handler.removeCallbacksAndMessages(null)
    }

    override fun setOnDataReady(player: Player): Boolean {
        var isSecondPicker = false
        if (player.playerNo == 1 && player.picked != "") {
            suit.player1.picked = player.picked
            Toast.makeText(
                this,
                "${suit.player1.name} memilih ${player.picked}",
                Toast.LENGTH_SHORT
            ).show()
            if (suit.type == "CPU") pickerPlayer2.cpuStartPicking()
            playerCount += 1
            if (playerCount == 2) {
                Controller(this).evaluate(suit)
                isSecondPicker = true
            }
        } else if (player.playerNo == 2 && player.picked != "") {
            suit.player2.picked = player.picked
            if (suit.type != "CPU") Toast.makeText(
                this,
                "${suit.player2.name} memilih ${player.picked}",
                Toast.LENGTH_SHORT
            ).show()
            playerCount += 1
            if (playerCount == 2) {
                Controller(this).evaluate(suit)
                isSecondPicker = true
            }
        } else {
            reset()
            isSecondPicker = false
        }
        return isSecondPicker
    }

    override fun result(suit: Suit) {
        this.suit = suit
        pickerPlayer1.setFinishedState()
        pickerPlayer2.setFinishedState()
        if (suit.type != "CPU") showDialog(this.suit.winner)
    }

    override fun setResultOnCPUFinished() {
        Toast.makeText(
            this,
            "CPU memilih ${this.suit.player2.picked}",
            Toast.LENGTH_SHORT
        ).show()
        showDialog(this.suit.winner)
    }

    private fun showDialog(string: String){
        handler.postDelayed({
            val resultDialog = ResultDialogFragment(string)
            resultDialog.show(supportFragmentManager, null)
        }, 2000)
    }

    fun reset() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}