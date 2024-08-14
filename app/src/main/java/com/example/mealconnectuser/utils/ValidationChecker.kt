package com.example.mealconnectuser.utils

import android.util.Patterns
import java.util.regex.Pattern




    fun validateEmail(email:String):RegisterValidation{

        if (email.isEmpty()) return RegisterValidation.Failed("Email cannot be Empty")

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return RegisterValidation.Failed("Wrong email format")


        return RegisterValidation.Success
    }

fun validatePassword(password:String):RegisterValidation{

    if (password.isEmpty()) return RegisterValidation.Failed("Password cannot be empty")

    if (password.length<6) return RegisterValidation.Failed("Password must contains 6 characters")

    return RegisterValidation.Success

}
