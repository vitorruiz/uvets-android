package br.com.uvets.uvetsandroid.data.model

import android.os.Parcel
import android.os.Parcelable

data class Pet(val name: String,
               val birth: Long,
               val race: String,
               val gender: String,
               val photoUrl: String?,
               val castrated: Boolean,
               val weight: Double?,
               val chipNumber: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(birth)
        parcel.writeString(race)
        parcel.writeString(gender)
        parcel.writeString(photoUrl)
        parcel.writeByte(if (castrated) 1 else 0)
        parcel.writeValue(weight)
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