package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName
import java.util.*

class VetScheduleVO(
    @SerializedName("treatmentDuration")
    var treatmentDuration: String,
    @SerializedName("date")
    var date: Date,
    @SerializedName("hours")
    var hours: MutableList<String>
)