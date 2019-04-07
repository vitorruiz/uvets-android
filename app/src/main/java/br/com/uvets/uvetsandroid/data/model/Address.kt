package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Address(
    val formatted: String,
    val lat: Double? = null,
    val lon: Double? = null,
    var id: Long
) : Parcelable