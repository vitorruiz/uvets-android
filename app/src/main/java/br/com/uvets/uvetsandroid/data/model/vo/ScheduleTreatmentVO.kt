package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName

class ScheduleTreatmentVO(
    @SerializedName("petId")
    var petId: Long,
    @SerializedName("date")
    var date: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("observations")
    var observations: String?
)