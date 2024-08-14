package com.example.mealconnectuser.utils

import com.example.mealconnectuser.PlaceOrder

class UserData{
    var id:String?=null
    var username:String?=null
    var phoneno:String?=null
    var email:String?=null
    var password:String?=null
    lateinit var profileimage:String
    var foodItems: List<PartnerData> = listOf()
    var isOrderPlaceOrder:Boolean = false


    constructor()

     constructor(id:String,username:String,email:String,password:String){
         this.id=id
       this.username=username
       this.email=email
       this.password=password
       this.phoneno=""
       this.profileimage=""

    }



}


