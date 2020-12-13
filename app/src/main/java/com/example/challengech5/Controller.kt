package com.example.challengech5

import com.example.challengech5.listener.CallbackResult
import com.example.challengech5.model.Suit

class Controller(private var viewSuit: CallbackResult) {

    fun evaluate(suit: Suit) {
        //player 1 win
        if (((suit.player1.picked == "batu") && (suit.player2.picked == "gunting")) ||
            ((suit.player1.picked == "kertas") && (suit.player2.picked == "batu")) ||
            ((suit.player1.picked == "gunting") && (suit.player2.picked == "kertas"))
        ) suit.winner = "${suit.player1.name} \n MENANG!"

        //player 2 / CPU win
        else if (((suit.player2.picked == "batu") && (suit.player1.picked == "gunting")) ||
            ((suit.player2.picked == "kertas") && (suit.player1.picked == "batu")) ||
            ((suit.player2.picked == "gunting") && (suit.player1.picked == "kertas"))
        ) suit.winner = "${suit.player2.name} \n MENANG!"

        //draw
        else suit.winner = "SERI!"

        this.viewSuit.result(suit)
    }
}