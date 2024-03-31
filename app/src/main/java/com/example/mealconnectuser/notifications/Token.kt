package com.example.mealconnectuser.notifications

class Token {

    private var token:String=""

    constructor(token: String){
        this.token=token
    }

    fun getToken():String{
        return this.token
    }

    fun setToken(newtoken: String){
        this.token=newtoken
    }
}