package com.example.mealconnectuser.room


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.mealconnectuser.utils.Partner

@Entity("Meal_table")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
     val id:Int=0,
     val meal:Partner
){



}
