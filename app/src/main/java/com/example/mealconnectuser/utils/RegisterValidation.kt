package com.example.mealconnectuser.utils

sealed class RegisterValidation() {
    data object Success:RegisterValidation()
    data class Failed(var message:String):RegisterValidation()
}

data class RegisterFieldState(
    val email:RegisterValidation,
    val password:RegisterValidation
)