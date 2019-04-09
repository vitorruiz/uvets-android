package br.com.uvets.uvetsandroid.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "pets")
@Parcelize
data class Pet(
    @PrimaryKey
    var id: Long?,
    var name: String,
    var birth: Date,
    var race: String,
    var gender: String,
    var photoUrl: String?,
    var castrated: Boolean,
    var weight: Double,
    var chipNumber: String?
) : Parcelable