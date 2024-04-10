package com.example.mealconnectuser.room

import androidx.room.TypeConverter
import com.example.mealconnectuser.utils.LocationData
import com.example.mealconnectuser.utils.PartnerData
import com.google.gson.Gson

class Convertor {


    private val gson = Gson()

    @TypeConverter
    fun fromPartner(partnerData: PartnerData): String {
        return gson.toJson(partnerData)
    }

    @TypeConverter
    fun toPartner(partnerString: String): PartnerData {
        return gson.fromJson(partnerString, PartnerData::class.java)
    }

    @TypeConverter
    fun fromLocationData(locationData: LocationData): String {
        return gson.toJson(locationData)
    }

    @TypeConverter
    fun toLocationData(json: String): LocationData {
        return gson.fromJson(json, LocationData::class.java)
    }


    @TypeConverter
    fun fromList(list: List<Double>): Double {
        return list[0] // Assuming you only expect one value in the list
    }

    @TypeConverter
    fun toList(value: Double): List<Double> {
        return listOf(value)
    }
}