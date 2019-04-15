package br.com.uvets.uvetsandroid.data.database

import androidx.room.TypeConverter
import br.com.uvets.uvetsandroid.data.model.Address
import com.google.gson.Gson
import java.util.*


class DateTypeConverter {

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return (value?.time)
    }
}

class AddressTypeConverter {

    @TypeConverter
    fun toAddress(value: String?): Address? {
        return if (value == null) null else Gson().fromJson(value, Address::class.java)
    }

    @TypeConverter
    fun toJsonString(address: Address?): String? {
        return Gson().toJson(address)
    }
}