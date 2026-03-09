package hu.bme.aut.android.recept.data

import hu.bme.aut.android.recept.data.api.MealAPI
import hu.bme.aut.android.recept.data.database.MealDAO
import hu.bme.aut.android.recept.data.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val api: MealAPI,
    private val dao: MealDAO
){

    //search for meals by name
    suspend fun searchMeals(query: String): List<Meal> = withContext(Dispatchers.IO) {
        try {
            val response = api.searchMeals(query)
            // if no response, return empty list (make null to empty list)
            response.meals ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            print("Error: ${e.message}")
            emptyList()
        }
    }

    //search meals by first letter
    suspend fun searchMealsByFirstLetter(letter: String): List<Meal> = withContext(Dispatchers.IO) {
        try {
            val response = api.searchMealsByFirstLetter(letter)
            response.meals ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    //search meal by id
    suspend fun searchMealById(id: String): Meal? = withContext(Dispatchers.IO) {
        try {
            val response = api.searchMealById(id)
            response.meals?.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    //save the meal to the favourites
    suspend fun saveMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dao.insertMeal(meal)
    }

    //delete from favourite
    suspend fun deleteMeal(meal: Meal) = withContext(Dispatchers.IO) {
        dao.deleteMeal(meal)
    }

    //get all meals from favourite
    fun getSavedMeals(): Flow<List<Meal>> {
        return dao.getAllMeals()
    }

    //check if its saved
    suspend fun isMealSaved(id: String): Boolean = withContext(Dispatchers.IO) {
        dao.isMealSaved(id)
    }

    //get saved meals by id
    suspend fun getSavedMealId(id: String): Meal? = withContext(Dispatchers.IO) {
        dao.getMealById(id)
    }
}
