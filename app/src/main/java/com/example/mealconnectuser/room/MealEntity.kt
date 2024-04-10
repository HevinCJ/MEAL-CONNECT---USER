package com.example.mealconnectuser.room


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mealconnectuser.utils.PartnerData

@Entity("Meal_table")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
     val id:Int=0,
    @Embedded(prefix = "partner_")
     val meal:PartnerData
)




