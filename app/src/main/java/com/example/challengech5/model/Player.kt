package com.example.challengech5.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Player(var name: String, var playerNo: Int?, var picked: String = "") :
    Parcelable


