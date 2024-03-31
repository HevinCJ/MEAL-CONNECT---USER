package com.example.mealconnectuser.utils

import kotlinx.parcelize.RawValue

data class Partner(
    var id: String = "",
    var mealname: String = "",
    var image: String = "",
    var phoneno: String = "",
    var amount: String = "",
    var descp: String = "",
    var location: @RawValue LocationData = LocationData()
)
