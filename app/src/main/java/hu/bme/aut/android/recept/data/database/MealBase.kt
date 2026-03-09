package hu.bme.aut.android.recept.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.bme.aut.android.recept.data.model.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealBase : RoomDatabase() {

    abstract fun mealDao(): MealDAO
}