package br.com.uvets.uvetsandroid.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Pet(
    var id: Long?,
    var name: String,
    var birth: Date,
    var race: String,
    var gender: String,
    var photoUrl: String?,
    var castrated: Boolean,
    var weight: Double,
    var chipNumber: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readSerializable() as Date,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeSerializable(birth)
        parcel.writeString(race)
        parcel.writeString(gender)
        parcel.writeString(photoUrl)
        parcel.writeByte(if (castrated) 1 else 0)
        parcel.writeDouble(weight)
        parcel.writeString(chipNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }


}