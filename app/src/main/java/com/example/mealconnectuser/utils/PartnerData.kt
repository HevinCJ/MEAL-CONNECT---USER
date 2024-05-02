package com.example.mealconnectuser.utils

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


class PartnerData(){

    var id: String = ""
    var key: String = ""
    var mealname: String = ""
    var image: String = ""
    var phoneno: String = ""
    var amount: String = ""
    var descp: String = ""
    var partnerquantity: String = ""
    var addedToCart: Boolean = false
    var location: LocationData = LocationData()
    var timestamp: Long = 0L
    var userquantity:String = ""

    constructor(
        key: String,
        amount: String,
        userquantity:String
    ) : this() {
        this.key = key
        this.amount = amount
        this.userquantity=userquantity
    }

    constructor(
        id:String,
        key: String,
        mealname: String,
        image: String,
        phoneno: String,
        amount: String,
        descp: String,
        quantity: String,
        location: LocationData,
        timestamp: Long
    ) : this() {
        this.id = id
        this.key = key
        this.mealname = mealname
        this.image = image
        this.amount = amount
        this.descp = descp
        this.partnerquantity = quantity
        this.location = location
        this.phoneno = phoneno
        this.addedToCart = false
        this.timestamp = timestamp
    }
}

