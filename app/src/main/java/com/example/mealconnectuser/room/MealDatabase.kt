package com.example.mealconnectuser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
@TypeConverters(Convertor::class)
abstract class MealDatabase:RoomDatabase() {

abstract fun mealDao():MealDao

companion object{

    private var INSTANCE:MealDatabase?=null

    fun getInstance(context:Context):MealDatabase{
        val tempInstance = INSTANCE
        if (tempInstance!=null){
            return tempInstance
        }else{
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "Meal_Database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}



}