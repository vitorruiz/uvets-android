package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Address(
    @SerializedName("id")
    var id: Long,
    @SerializedName("formatted")
    val formatted: String,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null
) : Parcelable