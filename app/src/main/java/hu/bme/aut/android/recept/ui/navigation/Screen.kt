package hu.bme.aut.android.recept.ui.navigation

import kotlinx.serialization.Serializable

object Screen {

    @Serializable
    object MealList

    @Serializable
    data class MealDetail(val id: String)

    @Serializable
    object Timer
}