package com.example.challengech5.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Suit(
    var player1: Player,
    var player2: Player,
    var type: String,
    var winner: String
) : Parcelable