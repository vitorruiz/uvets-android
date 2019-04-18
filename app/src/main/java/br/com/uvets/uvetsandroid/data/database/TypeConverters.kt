package br.com.uvets.uvetsandroid.data.database

import androidx.room.TypeConverter
import br.com.uvets.uvetsandroid.data.model.Address
import br.com.uvets.uvetsandroid.data.model.VetService
import br.com.uvets.uvetsandroid.fromJson
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
    fun toJson(address: Address?): String? {
        return Gson().toJson(address)
    }
}

class VetServicesTypeConverter {

    @TypeConverter
    fun toList(value: String?): List<VetService>? =
        if (value == null) null else Gson().fromJson<List<VetService>>(value)

    @TypeConverter
    fun toJson(vetServices: List<VetService>?): String? = Gson().toJson(vetServices)
}