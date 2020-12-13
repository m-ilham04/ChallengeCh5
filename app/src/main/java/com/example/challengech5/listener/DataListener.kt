package com.example.challengech5.listener

import com.example.challengech5.model.Player

interface DataListener {
    fun setOnDataReady(player: Player): Boolean
    //Khusus CPU Player
    fun setResultOnCPUFinished()
}