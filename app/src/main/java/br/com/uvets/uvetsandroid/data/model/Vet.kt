package br.com.uvets.uvetsandroid.data.model

data class Vet(
    val name: String,
    val doc: String,
    val phone: String,
    val email: String,
    val address: String,
    val crmv: String,
    val classification: String,
    val rating: Double
)