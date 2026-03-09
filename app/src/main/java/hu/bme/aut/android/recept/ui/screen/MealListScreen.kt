package hu.bme.aut.android.recept.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.bme.aut.android.recept.data.model.Meal
import hu.bme.aut.android.recept.ui.viewmodel.MealViewModel


@Composable
fun MealListScreen(
    viewModel: MealViewModel = hiltViewModel(),
    onMealClick: (String) -> Unit = {},
    onTimerClick: () -> Unit = {}
) {
    // auto refresh if seachMealRes, or seachQuery change
    val meals by viewModel.searchMealRes.collectAsState()
    val searchText by viewModel.searchQuery.collectAsState()
    val savedMeals by viewModel.savedMeals.collectAsState()
    var showFavourites by rememberSaveable { mutableStateOf(false) }

    //if favourite is clicked show favourite else show all meals
    val mealsToShow = if (showFavourites) savedMeals else meals


    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LargeFloatingActionButton(
                    onClick = onTimerClick,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Timer"
                    )
                }
                LargeFloatingActionButton(
                    onClick = { showFavourites = !showFavourites },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = if (showFavourites) Icons.Default.Home else Icons.Default.Favorite,
                        contentDescription = "favourite",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ){innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            //if not favourite show the search
            if (!showFavourites) {
                //search field
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.search(it) }, // trigger search when text changes
                    label = { Text("Search meal") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            else{
                Text(
                    text = "My Favourites",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }


            if (mealsToShow.isEmpty()) {
                //if not data
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text =
                            if (showFavourites) {
                                "No favourite found"
                            } else {
                                "No meal found"
                            }, color = Color.Red
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(mealsToShow) { meal ->
                        MealItem(meal = meal, onClick = { onMealClick(meal.idMeal) })
                    }
                }
            }
        }
    }


}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${meal.strArea} | ${meal.strCategory}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}