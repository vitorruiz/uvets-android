package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName

data class LoginRequestVO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)