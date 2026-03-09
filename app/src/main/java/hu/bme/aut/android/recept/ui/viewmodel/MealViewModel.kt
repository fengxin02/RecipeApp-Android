package hu.bme.aut.android.recept.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.recept.data.MealRepository
import hu.bme.aut.android.recept.data.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val rep: MealRepository
) : ViewModel() {


    private val _searchMealRes = MutableStateFlow<List<Meal>>(emptyList())
    val searchMealRes: StateFlow<List<Meal>> = _searchMealRes.asStateFlow()

    //if screen redraw the query will be saved on the screen
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //detail meal
    private val _selectedMeal = MutableStateFlow<Meal?>(null)
    val selectedMeal: StateFlow<Meal?> = _selectedMeal.asStateFlow()

    init {
        loadDefaultMeals()
    }

    //convert flow into stateflow
    val savedMeals: StateFlow<List<Meal>> = rep.getSavedMeals().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), //5sec disconnect
        initialValue = emptyList() //ini
    )

    private fun loadDefaultMeals() {
        viewModelScope.launch {
            val results = rep.searchMealsByFirstLetter("a")
            _searchMealRes.value = results
        }
    }

    fun loadMealDetail(mealId: String) {
        viewModelScope.launch {
            //search "a" first
            val fromSearch = _searchMealRes.value.find { it.idMeal == mealId }

            val fromDatabase = rep.getSavedMealId(mealId)

            if (fromSearch != null) {
                _selectedMeal.value = fromSearch
            }else if (fromDatabase != null) {
                //from local
                _selectedMeal.value = fromDatabase
            } else {
                //if no local find online
                val online = rep.searchMealById(mealId)
                _selectedMeal.value = online
            }
        }
    }

    fun search(query: String) {
        _searchQuery.value = query

        if (query.isBlank()) {
            loadDefaultMeals()
            //_searchMealRes.value = emptyList()
            return
        }
        viewModelScope.launch {
            val res = rep.searchMeals(query)
            _searchMealRes.value = res
        }
    }

    fun changeFavourite(meal: Meal) {
        viewModelScope.launch {
            //if exists delete
            if (rep.isMealSaved(meal.idMeal)) {
                rep.deleteMeal(meal)
            } else {
                //if not exists add in favourite
                rep.saveMeal(meal)
            }
        }
    }

    //check fav
    suspend fun isMealFavorite(id: String): Boolean {
        return rep.isMealSaved(id)
    }


}