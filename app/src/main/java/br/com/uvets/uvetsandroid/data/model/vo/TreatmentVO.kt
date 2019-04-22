package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName


class TreatmentVO(
    @SerializedName("id")
    var id: Long,
    @SerializedName("pet")
    var pet: String,
    @SerializedName("veterinary")
    var veterinary: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("accepted")
    var accepted: Boolean,
    @SerializedName("responsible")
    var responsible: String,
    @SerializedName("observations")
    var observations: String?
)