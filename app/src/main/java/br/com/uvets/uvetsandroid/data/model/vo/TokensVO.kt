package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName

class TokensVO(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)