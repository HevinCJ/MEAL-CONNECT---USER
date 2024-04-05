package com.example.mealconnectuser.utils

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.RawValue

@Entity(tableName = "Partner_table")
data class Partner(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var mealname: String = "",
    var image: String = "",
    var phoneno: String = "",
    var amount: String = "",
    var descp: String = "",
    var location: @RawValue LocationData = LocationData()
)
