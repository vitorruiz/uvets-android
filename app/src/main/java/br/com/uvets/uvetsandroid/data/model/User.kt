package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val address: Address,
    val doc: String,
    val email: String,
    val id: Long,
    val name: String,
    val phone: String
    //val roles: List<String>
) : Parcelable