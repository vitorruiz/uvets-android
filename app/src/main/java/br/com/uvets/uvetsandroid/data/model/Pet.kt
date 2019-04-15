package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "pets")
@Parcelize
data class Pet(
    @SerializedName("id")
    @PrimaryKey
    var id: Long?,
    @SerializedName("name")
    var name: String,
    @SerializedName("birth")
    var birth: Date,
    @SerializedName("race")
    var race: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("photoUrl")
    var photoUrl: String?,
    @SerializedName("castrated")
    var castrated: Boolean,
    @SerializedName("weight")
    var weight: Double,
    @SerializedName("chipNumber")
    var chipNumber: String?
) : Parcelable