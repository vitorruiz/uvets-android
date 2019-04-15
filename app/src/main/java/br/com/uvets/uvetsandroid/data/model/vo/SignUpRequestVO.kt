package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName

data class SignUpRequestVO(
    @SerializedName("name")
    val name: String,
    @SerializedName("doc")
    val doc: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("password")
    val password: String
)