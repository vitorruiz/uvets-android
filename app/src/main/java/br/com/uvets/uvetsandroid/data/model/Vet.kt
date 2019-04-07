package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vet(
    val name: String,
    val doc: String,
    val phone: String,
    val email: String,
    val address: Address,
    val crmv: String,
    val classification: String,
    val rating: Double
) : Parcelable