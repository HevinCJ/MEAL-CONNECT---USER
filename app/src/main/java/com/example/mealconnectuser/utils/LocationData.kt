package com.example.mealconnectuser.utils

import com.google.firebase.database.PropertyName

data class LocationData(
    @get:PropertyName("latitude") var latitude: Double? = 0.0,
    @get:PropertyName("longitude") var longitude: Double? = 0.0
)