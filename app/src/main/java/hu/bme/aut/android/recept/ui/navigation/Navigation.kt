package hu.bme.aut.android.recept.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import hu.bme.aut.android.recept.ui.screen.MealDetailScreen
import hu.bme.aut.android.recept.ui.screen.MealListScreen
import hu.bme.aut.android.recept.ui.screen.TimerScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MealList) {

        composable<Screen.MealList> {
            MealListScreen(
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail(id = mealId))
                },
                onTimerClick = {
                    navController.navigate(Screen.Timer)
                }
            )
        }

        composable<Screen.MealDetail> { backStackEntry ->
            val detail: Screen.MealDetail = backStackEntry.toRoute()

            MealDetailScreen(
                mealId = detail.id,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Timer> {
            TimerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }

}