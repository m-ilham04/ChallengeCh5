package com.example.challengech5

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.challengech5.model.Player
import com.example.challengech5.model.Suit
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {

    private lateinit var suit: Suit
    private lateinit var player1: Player

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        player1 = intent.getParcelableExtra<Player>("Player") as Player

        val textVsPemain = "${player1.name} VS Pemain"
        findViewById<TextView>(R.id.tvVsPlayer).text = textVsPemain

        val textVsCPU = "${player1.name} VS CPU"
        findViewById<TextView>(R.id.tvVsCPU).text = textVsCPU

        val snackbar = Snackbar.make(
            findViewById(R.id.menuActivity),
            "Selamat datang ${player1.name}",
            Snackbar.LENGTH_INDEFINITE
        )

        snackbar.setActionTextColor(Color.parseColor(resources.getString(R.color.orange)))
        snackbar.setTextColor(Color.WHITE)

        snackbar.setAction("Tutup") {
            snackbar.dismiss()
        }

        val tvSnackbar = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
        tvSnackbar.setTypeface(resources.getFont(R.font.comic_sans_ms), Typeface.BOLD)
        tvSnackbar.setTextSize(20f)

        snackbar.show()

        val btnVsPlayer = findViewById<ImageView>(R.id.ivVsPlayer)
        btnVsPlayer.setOnClickListener {
            suit = Suit(
                player1, Player("Pemain 2", 2), "PVP", ""
            )
            val intent = Intent(this, SuitGameActivity::class.java)
            intent.putExtra("Game", suit)
            startActivity(intent)
        }

        val btnVsCPU = findViewById<ImageView>(R.id.ivVsCPU)
        btnVsCPU.setOnClickListener {
            suit = Suit(
                player1, Player("CPU", 2), "CPU", ""
            )
            val intent = Intent(this, SuitGameActivity::class.java)
            intent.putExtra("Game", suit)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        exitProcess(0)
    }
}