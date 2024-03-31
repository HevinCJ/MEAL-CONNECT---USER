package com.example.mealconnectuser.room

import androidx.room.TypeConverter
import com.example.mealconnectuser.utils.Partner
import com.google.gson.Gson

class Convertor {


    private val gson = Gson()

    @TypeConverter
    fun fromPartner(partner: Partner): String {
        return gson.toJson(partner)
    }

    @TypeConverter
    fun toPartner(partnerString: String): Partner {
        return gson.fromJson(partnerString, Partner::class.java)
    }


}