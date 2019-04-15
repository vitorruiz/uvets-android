package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("address")
    val address: Address,
    @SerializedName("doc")
    val doc: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
    //val roles: List<String>
) : Parcelable