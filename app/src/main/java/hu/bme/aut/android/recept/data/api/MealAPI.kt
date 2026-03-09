package hu.bme.aut.android.recept.data.api

import hu.bme.aut.android.recept.data.model.MealListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI{
    //search by name
    //https://www.themealdb.com/api/json/v1/1/search.php?s=Chicken
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String // query parameter
    ): MealListResponse

    //search by first letter
    @GET("search.php")
    suspend fun searchMealsByFirstLetter(@Query("f") letter: String): MealListResponse

    //check detail for specific meal
    @GET("lookup.php")
    suspend fun searchMealById(@Query("i") id: String): MealListResponse
}