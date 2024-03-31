package com.example.mealconnectuser.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mealconnectuser.utils.Partner

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(meal:MealEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM MEAL_TABLE ORDER BY ID ASC")
    fun getallMeal():LiveData<List<MealEntity>>




}