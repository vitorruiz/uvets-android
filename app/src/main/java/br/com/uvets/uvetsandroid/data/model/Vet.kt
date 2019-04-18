package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "vets")
@Parcelize
data class Vet(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("doc")
    val doc: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("address")
    val address: Address,
    @SerializedName("crmv")
    val crmv: String,
    @SerializedName("classification")
    val classification: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("services")
    val services: List<VetService>
) : Parcelable