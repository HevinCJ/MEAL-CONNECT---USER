package com.example.mealconnectuser.repository

import com.example.mealconnectuser.room.MealDao
import com.example.mealconnectuser.room.MealEntity

class MealRepository(private val mealDao: MealDao) {

    val getallMeal=mealDao.getallMeal()

    val getTotalAmount = mealDao.getTotalAmount()

    suspend fun insertMeal(mealEntity:MealEntity){
        mealDao.insertMeal(mealEntity)

    }

    suspend fun updateMeal(mealEntity: MealEntity){
        mealDao.updateMeal(mealEntity)
    }

    suspend fun deleteMeal(mealEntity: MealEntity){
        mealDao.deleteMeal(mealEntity)
    }




}