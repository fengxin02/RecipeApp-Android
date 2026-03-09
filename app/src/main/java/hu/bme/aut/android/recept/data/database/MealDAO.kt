package hu.bme.aut.android.recept.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.recept.data.model.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {
    //if user favourite twice then the old one will be replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal : Meal)

    //delete a meal
    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<Meal>>

    //check if meal is saved
    @Query("SELECT EXISTS(SELECT * FROM meals WHERE idMeal = :id)")
    suspend fun isMealSaved(id: String): Boolean

    //get saved meals
    @Query("SELECT * FROM meals WHERE idMeal = :id")
    suspend fun getMealById(id: String): Meal?
}