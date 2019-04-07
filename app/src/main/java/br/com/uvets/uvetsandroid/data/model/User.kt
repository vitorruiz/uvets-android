package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val address: String,
    val doc: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String
    //val roles: List<String>
) : Parcelable