package br.com.uvets.uvetsandroid.data.model.vo

data class SignUpRequestVO(
    val name: String,
    val doc: String,
    val phone: String,
    val email: String,
    val address: String,
    val password: String
)